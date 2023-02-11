package com.driver;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderService {
    OrderRepository orderRepository = new OrderRepository();

    public String addOrder(Order order) {
        String result = orderRepository.addOrder(order);
        return result;
    }

    public String addPartner(String partnerId) {
        String result = orderRepository.addPartner(partnerId);
        return result;
    }

    public String addOrderPartnerPair(String orderId, String partnerId) {


        String result = orderRepository.addOrderPartnerPair(orderId, partnerId);
        return result;
    }

    public Order getOrderById(String orderId) {

        Order result = orderRepository.getOrderById(orderId);
        return result;
    }

    public DeliveryPartner getPartnerById(String partnerId) {

        DeliveryPartner result = orderRepository.getPartnerById(partnerId);
        return result;
    }

    public int getOrderCountByPartnerId(String partnerId) {

        int result = orderRepository.getOrderCountByPartnerId(partnerId);
        return result;
    }

    public List<String> getOrdersByPartnerId(String partnerId) {

        List<String> result = orderRepository.getOrdersByPartnerId(partnerId);
        return result;
    }

    public List<String> getAllOrders() {
        List<String> result = orderRepository.getAllOrders();
        return result;
    }

    public int getCountOfUnassignedOrders() {
        // Count of orders that have not been assigned to any DeliveryPartner
        int countOfOrders = orderRepository.getCountOfUnassignedOrders();
        return countOfOrders;
    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        // countOfOrders that are left after a particular time of a DeliveryPartner
        int countOfOrders = orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(time, partnerId);
        return countOfOrders;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {

        String time = orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
        return time;
    }

    public String deletePartnerById(String partnerId) {

        String result = orderRepository.deletePartnerById(partnerId);
        return result;
    }

    public String deleteOrderById(String orderId) {

        String result = orderRepository.deleteOrderById(orderId);
        return result;
    }
}