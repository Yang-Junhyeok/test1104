package com.example.test1104.service;

import com.example.test1104.dto.CartItemDto;
import com.example.test1104.entity.Cart;
import com.example.test1104.entity.CartItem;
import com.example.test1104.entity.Item;
import com.example.test1104.entity.Member;
import com.example.test1104.repository.CartItemRepository;
import com.example.test1104.repository.CartRepository;
import com.example.test1104.repository.ItemRepository;
import com.example.test1104.repository.MemberRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public Long addCart(CartItemDto cartItemDto, String username){
    Item item = itemRepository.findById(cartItemDto.getItemId())
            .orElseThrow(EntityExistsException::new);
    Member member = memberRepository.findByUsername(username);

    Cart cart = cartRepository.findByMemberId(member.getId());
        if (cart == null){
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());
        if (savedCartItem != null){
            savedCartItem.addCount(cartItemDto.getCount());
            return savedCartItem.getId();
        }else{
            CartItem cartItem = CartItem.createCartItem(cart,item, cartItemDto.getCount());
            cartItemRepository.save(cartItem);
            return cartItem.getId();
        }

    }
}
