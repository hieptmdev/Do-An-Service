package com.datn.service;

import com.datn.config.JwtConfig;
import com.datn.dto.BaseDto;
import com.datn.dto.CartDTO;
import com.datn.dto.ProductDto;
import com.datn.entity.Cart;
import com.datn.entity.CartDetaill;
import com.datn.entity.Product;
import com.datn.entity.User;
import com.datn.repository.CartDetailRepository;
import com.datn.repository.CartRepository;
import com.datn.repository.UserRepository;
import com.datn.service.iservice.CartService;
import com.datn.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public <T extends BaseDto> List<T> findAll() {
        return null;
    }

    @Override
    @Transactional
    public CartDTO saveOrUpdate(HttpServletRequest request, Object object) {
        //chuyển Obj -> DTO
        ProductDto productDto = (ProductDto) object;
        CartDetaill cartDetaill;
        Cart cart;
        User user = null;
        String authorization = request.getHeader("Authorization");

        if (!AppUtil.isNullOrEmpty(authorization)){
            String token = authorization.replace("Bearer ", "");
            String username = jwtConfig.getUsernameFromJwtToken(token);
            user = userRepository.findByUsername(username);
        }
        //đã đăng nhập
        if (user != null){
            cart = cartRepository.findByUser(user);
            //trong cart khác null
            if (cart != null){
                //lưu mới thêm cartdeatail
                cartDetaill = new CartDetaill();
                //thêm sản phẩm vào trong cartdetail
                cartDetaill.setProduct(AppUtil.mapperEntAndDto(productDto, Product.class));
                cartDetaill.setNumberPro(1L);
                cartDetaill.setCart(cart);
                cart.getCartDetaills().add(cartDetailRepository.save(cartDetaill));
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
                cartDetaill.setProduct(AppUtil.mapperEntAndDto(productDto, Product.class));
                cartDetaill.setNumberPro(1L);
                cartDetaill.setCart(cart);
                //trong khi cart có sản phẩm -> lưu detaill
                if (cart.getCartDetaills() != null) {
                    cart.getCartDetaills().add(cartDetailRepository.save(cartDetaill));

                }
                // trong khi không có sản phẩm
                else {
                    //tạo ra 1 lsist
                    List<CartDetaill> data = new ArrayList<>();
                    data.add(cartDetailRepository.save(cartDetaill));
                    cart.setCartDetaills(data);
                }
                cart = cartRepository.save(cart);
            }
            return AppUtil.mapperEntAndDto(cart, CartDTO.class);
        }
        //chưa đăng nhập
        else {
            if (AppUtil.NVL(productDto.getCartId()) == 0L) {
                cart = new Cart();
                cart.setUser(user);
                cart = cartRepository.save(cart);
                cartDetaill = new CartDetaill();
                cartDetaill.setProduct(AppUtil.mapperEntAndDto(productDto, Product.class));
                cartDetaill.setNumberPro(1L);
                cartDetaill.setCart(cart);
                if (cart.getCartDetaills() != null) {
                    cart.getCartDetaills().add(cartDetailRepository.save(cartDetaill));

                }else {
                    List<CartDetaill> data = new ArrayList<>();
                    data.add(cartDetailRepository.save(cartDetaill));
                    cart.setCartDetaills(data);
                }
                cart = cartRepository.save(cart);
                return AppUtil.mapperEntAndDto(cart, CartDTO.class);
            }
            cart = cartRepository.findById(productDto.getCartId()).orElse(null);
            if (cart != null){
                cartDetaill = new CartDetaill();
                cartDetaill.setProduct(AppUtil.mapperEntAndDto(productDto, Product.class));
                cartDetaill.setCart(cart);
                cartDetaill.setNumberPro(1L);
                if (cart.getCartDetaills() != null) {
                    cart.getCartDetaills().add(cartDetailRepository.save(cartDetaill));
                } else {
                    List<CartDetaill> data = new ArrayList<>();
                    data.add(cartDetailRepository.save(cartDetaill));
                    cart.setCartDetaills(data);
                }
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
        return null;
    }
}
