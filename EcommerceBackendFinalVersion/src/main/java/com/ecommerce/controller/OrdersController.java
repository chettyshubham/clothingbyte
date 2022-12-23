package com.ecommerce.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.CartDTO;
import com.ecommerce.dto.OrderDetailsDTO;
import com.ecommerce.dto.OrderResponseDTO;
import com.ecommerce.dto.PlaceOrderDTO;
import com.ecommerce.dto.ProductResponseDTO;
import com.ecommerce.dto.Response;
import com.ecommerce.entities.Address;
import com.ecommerce.entities.Customer;
import com.ecommerce.entities.Order;
import com.ecommerce.entities.OrderDetails;
import com.ecommerce.entities.Payment;
import com.ecommerce.entities.Product;
import com.ecommerce.service.AddressService;
import com.ecommerce.service.CustomerService;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.OrderdetailService;
import com.ecommerce.service.PaymentService;
import com.ecommerce.service.ProductService;

@CrossOrigin
@RestController
@RequestMapping("/api/orders")
public class OrdersController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private OrderdetailService orderDetailsService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ModelMapper mapper;

	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	@PostMapping
	public ResponseEntity<?> save(@RequestBody PlaceOrderDTO dto) {
		Address address = addressService.saveAddress(dto.getAddress());
		dto.getPayment().setPaymentdate(LocalDateTime.now());
		Payment payment = paymentService.savePayment(dto.getPayment());
		Order order = new Order();
		order.setOrderDate(LocalDateTime.now());
		order.setAddress(address);
		order.setPayment(payment);
		Customer customer = customerService.findUserById(dto.getCustomerid());
		order.setCustomer(customer);
		Order orders = orderService.saveOrder(order);

		for (CartDTO cart : dto.getCart()) {
			OrderDetails od = new OrderDetails();
			od.setOrder(orders);
			od.setQty(cart.getQty());
			ProductResponseDTO productDTO = productService.findProductById(cart.getProdid());
			Product product = mapper.map(productDTO, Product.class);
			od.setProduct(product);
			orderDetailsService.saveOrderDetails(od);
		}
		return Response.status(HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
	@GetMapping
	public ResponseEntity<?> findAllOrders(@RequestParam Optional<Long> custid) {
		List<Order> result = null;
		if (custid.isPresent()) {
			Customer customer = customerService.findUserById(custid.get());
			result = orderService.getCustomerOrders(customer);
		} else {
			result = orderService.getAllOrders();
		}
		return Response.success(result);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
	@GetMapping("/{id}")
	public ResponseEntity<?> findOrderById(@PathVariable Long id) {
		Order order = orderService.findById(id);
		List<OrderDetails> details = orderDetailsService.findByOrder(order);
		List<OrderDetailsDTO> detailsdto = new ArrayList<OrderDetailsDTO>();
		details.forEach(od -> {
			OrderDetailsDTO dto = mapper.map(od, OrderDetailsDTO.class);
			detailsdto.add(dto);
		});
		OrderResponseDTO result = new OrderResponseDTO();
		result.setOrder(order);
		result.setDetails(detailsdto);
		return Response.success(result);
	}
}
