package com.datn.service;

import com.datn.config.JwtConfig;
import com.datn.dto.BaseDto;
import com.datn.dto.CartDTO;
import com.datn.dto.ProductDto;
import com.datn.entity.*;
import com.datn.repository.CartDetailRepository;
import com.datn.repository.CartRepository;
import com.datn.repository.ProductInfoRepository;
import com.datn.repository.UserRepository;
import com.datn.service.iservice.CartService;
import com.datn.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CartServiveImpl implements CartService {
    @Autowired
    private JwtConfig jwtConfig;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartDetailRepository cartDetailRepository;
    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public <T extends BaseDto> List<T> findAll() {
        return null;
    }

    @Override
    @Transactional
    public CartDTO saveOrUpdate(HttpServletRequest request, Object object) {
        //chuyển Obj -> DTO
        ProductDto productDto = (ProductDto) object;
        List<ProductInfo> productInfoList = new ArrayList<>();
        CartDetaill cartDetaill;
        Cart cart;
        User user = null;

        String authorization = request.getHeader("Authorization");
        if (!AppUtil.isNullOrEmpty(authorization)){
            String token = authorization.replace("Bearer ", "");
            String username = jwtConfig.getUsernameFromJwtToken(token);
            user = userRepository.findByUsername(username);
        }
        if (AppUtil.NVL(productDto.getProductInfoId()) == 0L){
            productInfoList = productInfoRepository.findAllByProductId(productDto.getId());
            productDto.setProductInfoId(productInfoList.get(0).getId());
        }
        //đã đăng nhập
        if (user != null){
            cart = cartRepository.findByUser(user);
            //trong cart khác null
            if (cart != null){
                if (cart.getCartDetaills() != null) {
                    CartDetaill d1 = cart.getCartDetaills().stream().filter(cd -> cd.getProductInfo().getId() == productDto.getProductInfoId()).findFirst().orElse(null);
                    if (d1 != null) {
                        cart.getCartDetaills().stream().forEach(cd -> {
                            if (cd.getProductInfo().getId() == productDto.getProductInfoId()) {
                                cd.setNumberPro(cd.getNumberPro() + 1);
                                cd.setTotalMoney(cd.getNumberPro() * cd.getProductInfo().getProduct().getPriceSell());
                            }
                        });
                    }else {
                        cartDetaill = new CartDetaill();
                        setProductInfo(cartDetaill, productInfoList, productDto.getProductInfoId());
                        cartDetaill.setNumberPro(productDto.getQuantity() != null ? productDto.getQuantity() : 1L);
                        cartDetaill.setCart(cart);
                        cartDetaill.setTotalMoney(cartDetaill.getNumberPro() * cartDetaill.getProductInfo().getProduct().getPriceSell());
                        cart.getCartDetaills().add(cartDetailRepository.save(cartDetaill));
                    }
                }
                // trong khi không có sản phẩm
                else {
                    cartDetaill = new CartDetaill();
                    //thêm sản phẩm vào trong cartdetail
                    setProductInfo(cartDetaill, productInfoList, productDto.getProductInfoId());
                    cartDetaill.setNumberPro(productDto.getQuantity() != null ? productDto.getQuantity() : 1L);
                    cartDetaill.setCart(cart);
                    cartDetaill.setTotalMoney(cartDetaill.getNumberPro() * cartDetaill.getProductInfo().getProduct().getPriceSell());
                    //tạo ra 1 lsist
                    List<CartDetaill> data = new ArrayList<>();
                    data.add(cartDetailRepository.save(cartDetaill));
                    cart.setCartDetaills(data);
                }
                cart.setTotalNumber((long) cart.getCartDetaills().size());
                setTotalMonneyCart(cart);
                //lưu mới thêm cartdeatail
                cart = cartRepository.save(cart);
            }
            // trong khi cart không có sản phẩm nào
            else {
                // tạo ra 1 cart mới
                cart = new Cart();
                //set User cho cart mua
                cart.setUser(user);
                cart = cartRepository.save(cart);
                cartDetaill = new CartDetaill();
                //lưu product -> chuyển Ety->dto
                setProductInfo(cartDetaill, productInfoList, productDto.getProductInfoId());
                cartDetaill.setNumberPro(productDto.getQuantity() != null ? productDto.getQuantity() : 1L);
                cartDetaill.setCart(cart);
                cartDetaill.setTotalMoney(cartDetaill.getNumberPro() * cartDetaill.getProductInfo().getProduct().getPriceSell());
                List<CartDetaill> data = new ArrayList<>();
                data.add(cartDetailRepository.save(cartDetaill));
                cart.setCartDetaills(data);
                cart.setTotalNumber((long) cart.getCartDetaills().size());
                setTotalMonneyCart(cart);
                cart = cartRepository.save(cart);
            }
            return AppUtil.mapperEntAndDto(cart, CartDTO.class);
        }
        //chưa đăng nhập
        else {
            if (AppUtil.NVL(productDto.getCartId()) == 0L) {
                cart = new Cart();
                cart = cartRepository.save(cart);
                cartDetaill = new CartDetaill();
                setProductInfo(cartDetaill, productInfoList, productDto.getProductInfoId());
                cartDetaill.setNumberPro(productDto.getQuantity() != null ? productDto.getQuantity() : 1L);
                cartDetaill.setCart(cart);
                cartDetaill.setTotalMoney(cartDetaill.getNumberPro() * cartDetaill.getProductInfo().getProduct().getPriceSell());
                List<CartDetaill> data = new ArrayList<>();
                data.add(cartDetailRepository.save(cartDetaill));
                cart.setCartDetaills(data);
                cart.setTotalNumber((long) cart.getCartDetaills().size());
                setTotalMonneyCart(cart);
                cart = cartRepository.save(cart);
                return AppUtil.mapperEntAndDto(cart, CartDTO.class);
            }
            cart = cartRepository.findById(productDto.getCartId()).orElse(null);
            if (cart != null){
                cartDetaill = new CartDetaill();
                setProductInfo(cartDetaill, productInfoList, productDto.getProductInfoId());
                cartDetaill.setCart(cart);
                cartDetaill.setNumberPro(productDto.getQuantity() != null ? productDto.getQuantity() : 1L);
                cartDetaill.setTotalMoney(cartDetaill.getNumberPro() * cartDetaill.getProductInfo().getProduct().getPriceSell());
                if (cart.getCartDetaills() != null) {
                    CartDetaill d1 = cart.getCartDetaills().stream().filter(cd -> cd.getId() == productDto.getId()).findFirst().orElse(null);
                    if (d1 != null) {
                        cart.getCartDetaills().stream().forEach(cd -> {
                            if (cd.getProductInfo().getId() == productDto.getProductInfoId()) {
                                cd.setNumberPro(cd.getNumberPro() + 1);
                            }
                        });
                    }else {
                        cartDetaill = new CartDetaill();
                        setProductInfo(cartDetaill, productInfoList, productDto.getProductInfoId());
                        cartDetaill.setNumberPro(productDto.getQuantity() != null ? productDto.getQuantity() : 1L);
                        cartDetaill.setCart(cart);
                        cartDetaill.setTotalMoney(cartDetaill.getNumberPro() * cartDetaill.getProductInfo().getProduct().getPriceSell());
                        cart.getCartDetaills().add(cartDetailRepository.save(cartDetaill));
                    }
                }
                // trong khi không có sản phẩm
                else {
                    cartDetaill = new CartDetaill();
                    //thêm sản phẩm vào trong cartdetail
                    setProductInfo(cartDetaill, productInfoList, productDto.getProductInfoId());
                    cartDetaill.setNumberPro(productDto.getQuantity() != null ? productDto.getQuantity() : 1L);
                    cartDetaill.setCart(cart);
                    cartDetaill.setTotalMoney(cartDetaill.getNumberPro() * cartDetaill.getProductInfo().getProduct().getPriceSell());
                    //tạo ra 1 lsist
                    List<CartDetaill> data = new ArrayList<>();
                    data.add(cartDetailRepository.save(cartDetaill));
                    cart.setCartDetaills(data);
                }
                cart.setTotalNumber((long) cart.getCartDetaills().size());
                setTotalMonneyCart(cart);
                cart = cartRepository.save(cart);
                return AppUtil.mapperEntAndDto(cart, CartDTO.class);
            }
            return null;
        }
    }

    @Override
    public <T extends BaseDto> T findById(HttpServletRequest request, Long id) {
        return null;
    }

    @Override
    public Boolean delete(HttpServletRequest request, Long id) {
        Cart cart = cartRepository.findById(id).orElse(null);
        if (cart != null) {
            cartRepository.delete(cart);
            return true;
            //xoa
        }
        return false;
    }
    @Override
    public CartDTO getByUser(HttpServletRequest request, String username) {
        User user = userRepository.findByUsername(username);
        Cart cart =  cartRepository.findByUser(user);

        return  AppUtil.mapperEntAndDto(cart != null ? cart : new Cart(), CartDTO.class); //test đã :v
    }

    @Override
    public Boolean deleteCartDetail(HttpServletRequest request, Long id) {
        User user = null;
        Cart cart = null;
        CartDetaill cartDetaill = null;
        try {
            cartDetaill = cartDetailRepository.findById(id).orElse(null);
            if (cartDetaill != null){
                cart = cartRepository.findById(cartDetaill.getCart().getId()).orElse(null);
                if (cart != null){
                    cart.setTotalNumber(cart.getTotalNumber() - 1);
                    cartDetailRepository.delete(cartDetaill);
                    cartRepository.save(cart);
                    return true;
                }
                return false;
            }
            return false;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

//        try {
//            String authorization = request.getHeader("Authorization");
//            if (!AppUtil.isNullOrEmpty(authorization)) {
//                String token = authorization.replace("Bearer ", "");
//                String username = jwtConfig.getUsernameFromJwtToken(token);
//                user = userRepository.findByUsername(username);
//            }
//            if( user != null) {
//                cart = cartRepository.findByUser(user);
//            }else {
//                Long cartID = cartRepository.findCartId();
//                cart = cartRepository.getOne(cartID);
//
//            }
//            for (CartDetaill cartDetail : cart.getCartDetaills()) {
//                if (cartDetail.getId().equals(id) ) {
//                    cartDetailRepository.delete(cartDetail);
//                    cart.setTotalNumber(cart.getTotalNumber()-1);
//                    cart.getCartDetaills().remove(cartDetail);
//                    cart = cartRepository.save(cart);
//                    return true;
//                }
//            }
//            return false;
//
//        } catch (Exception e) {
//            System.err.println(e);
//            return false;
//        }

    }

    public void setProductInfo(CartDetaill cartDetaill, List<ProductInfo> productInfoList, Long productInfoId){
        if (AppUtil.NVL(productInfoId) == 0l){
            if(productInfoList.size() > 0){
                cartDetaill.setProductInfo(productInfoList.get(0));
                return;
            }
        }
        cartDetaill.setProductInfo(productInfoRepository.findById(productInfoId).orElse(null));
        return;
    }

    public void setTotalMonneyCart(Cart cart) {
        AtomicReference<Long> total = new AtomicReference<>(0L);
        cart.getCartDetaills().stream().forEach(cd -> {
            total.updateAndGet(v -> v + cd.getTotalMoney());
        });
        cart.setTotalMonneyCart(total.get());
    }
}
