package com.datn.service;

import com.datn.dto.*;
import com.datn.entity.*;
import com.datn.repository.*;
import com.datn.service.iservice.OrderService;
import com.datn.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Override
    public List<OderDTO> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(obj -> {
                    OderDTO dto = AppUtil.mapperEntAndDto(obj, OderDTO.class);
                    switch (obj.getStatus()){
                        case 0:
                            dto.setStatusString("Đang lên đơn");
                            break;
                        case 1:
                            dto.setStatusString("Đang giao");
                            break;
                        case 2:
                            dto.setStatusString("Đã giao");
                            break;
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OderDTO saveOrUpdate(HttpServletRequest request, Object object) {
        Order order;
        OderDTO oderDTO = (OderDTO) object;
        if (oderDTO != null) {
            User user = oderDTO.getUsername() != null ? userRepository.findByUsername(oderDTO.getUsername()) : null;
            Cart cart = AppUtil.NVL(oderDTO.getCartId()) != 0L ? cartRepository.findById(oderDTO.getCartId()).orElse(null) : null;

            if (AppUtil.NVL(oderDTO.getId()) == 0L) {
                order = AppUtil.mapperEntAndDto(oderDTO, Order.class);
                order.setCreatedDate(new Date());
                order.setUpdatedDate(new Date());
                order.setCode(AppUtil.generateOrderCode());
                order.setUsernameKH(oderDTO.getTenNguoiNhan());
                order.setStatus(0);
                order.setUser(user);
            }
            //đã đăng nhập
            else {
                Order data = orderRepository.findById(oderDTO.getId()).orElse(null);
                if (data == null) {
                    return null;
                }
                order = AppUtil.mapperEntAndDto(oderDTO, Order.class);
                order.setCode(data.getCode());
                order.setCreatedDate(data.getCreatedDate());
                order.setUpdatedDate(new Date());
                order.setStatus(oderDTO.getStatus());
                order.setUsernameKH(data.getUsernameKH());
                order.setUser(user);
            }
            order = orderRepository.save(order);
            List<OrderDetail> orderDetails = new ArrayList<>();
            if (AppUtil.NVL(oderDTO.getId()) == 0L && cart != null) {
                Order finalOrder = order;
                cart.getCartDetaills().stream().forEach(cd -> {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(finalOrder);
                    orderDetail.setProductInfo(cd.getProductInfo());
                    orderDetail.setNumberProduct(cd.getNumberPro());
                    orderDetails.add(orderDetail);
                });
                order.setNumberProduct(cart.getTotalNumber());
                order.setTotalResult(cart.getTotalMonneyCart());
                orderDetailRepository.saveAll(orderDetails);
                // dat hang xong phai xoa cart
                cartDetailRepository.deleteAll(cart.getCartDetaills());
                cartRepository.delete(cart);
            }
           return AppUtil.mapperEntAndDto(orderRepository.save(order), OderDTO.class);

        }
        return null;
    }

    @Override
    public OderDTO findById(HttpServletRequest request, Long id) {
        Order oder = orderRepository.findById(id).orElse(null);
        if (oder != null) {
            return (AppUtil.mapperEntAndDto(oder, OderDTO.class));
        }
        return null;
    }

    @Override
    public Boolean delete(HttpServletRequest request, Long id) {
        Order oder = orderRepository.findById(id).orElse(null);
        if (oder != null) {
            orderRepository.delete(oder);
            return true;
        }

        return false;
    }

    //tìm kiếm đơn hàng theo IdUser
    @Override
    public List<OderDTO> findByUserId(HttpServletRequest request, Long id) {
        List<Order> oder = orderRepository.findAllByUserId(id);
        if (oder != null) {
            return oder.stream()
                    .map(obj -> {
                        OderDTO dto = AppUtil.mapperEntAndDto(obj, OderDTO.class);
                        switch (obj.getStatus()){
                            case 0:
                                dto.setStatusString("Đang lên đơn");
                                break;
                            case 1:
                                dto.setStatusString("Đang giao");
                                break;
                            case 2:
                                dto.setStatusString("Đã giao");
                                break;
                        }
                        return dto;
                    })
                    .collect(Collectors.toList());
        }
        return null;
    }

    //Tim kiem don hang
    @Override
    public List<OderDTO> search(HttpServletRequest request, OderDTO dto) {
        return orderRepository.search(dto.getCode().toLowerCase().trim())
                .stream()
                .map(oder -> AppUtil.mapperEntAndDto(oder, OderDTO.class))
                .collect(Collectors.toList());
    }


    //lấy ra chi tiết oder theo mã code
    @Override
    public List<OderDetailDTO> searchOderDetail(HttpServletRequest request, OderDTO dto) {
        return orderDetailRepository.findAllByCode(dto.getCode())
                .stream()
                .map(od -> AppUtil.mapperEntAndDto(od, OderDetailDTO.class))
                .collect(Collectors.toList());
    }
}
