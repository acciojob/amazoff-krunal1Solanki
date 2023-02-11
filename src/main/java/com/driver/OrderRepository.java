package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public class OrderRepository {
    Map<String, Order> orderMap = new HashMap<>();
    Map<String, DeliveryPartner> partnerMap = new HashMap<>();
    Map<String, List<String>> pairMap = new HashMap<>();
    Map<String, String> assignedMap = new HashMap<>();

    public String addOrder(Order order) {
        orderMap.put(order.getId(), order);
        return "Added";
    }

    public String addPartner(String partnerId) {
        DeliveryPartner partner = new DeliveryPartner(partnerId);
        partnerMap.put(partnerId, partner);
        return "Added";
    }

    public String addOrderPartnerPair(String orderId, String partnerId) {
        List<String> list = pairMap.getOrDefault(partnerId, new ArrayList<>());
        list.add(orderId);
        pairMap.put(partnerId, list);
        assignedMap.put(orderId, partnerId);
        DeliveryPartner partner = partnerMap.get(partnerId);
        partner.setNumberOfOrders(list.size());
        return "Added";

    }

    public Order getOrderById(String orderId) {
        return orderMap.getOrDefault(orderId,null);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return partnerMap.getOrDefault(partnerId,null);
    }

    public int getOrderCountByPartnerId(String partnerId) {
        int orders = pairMap.getOrDefault(partnerId, new ArrayList<>()).size();
        return orders;
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        List<String> orders = pairMap.getOrDefault(partnerId, new ArrayList<>());
        return orders;
    }

    public List<String> getAllOrders() {
        List<String> orders = new ArrayList<>();
        for (String s : orderMap.keySet()) {
            orders.add(s);
        }
        return orders;

    }

    public int getCountOfUnassignedOrders() {
        int countOfOrders = orderMap.size() - assignedMap.size();
        return countOfOrders;
    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        int countOfOrders = 0;
        List<String> list = pairMap.get(partnerId);
        int deliveryTime = Integer.parseInt(time.substring(0, 2)) * 60 + Integer.parseInt(time.substring(3));
        for (String s : list) {
            Order order = orderMap.get(s);
            if (order.getDeliveryTime() > deliveryTime) {
                countOfOrders++;
            }
        }
        return countOfOrders;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        String time = "";
        List<String> list = pairMap.get(partnerId);
        int deliveryTime = 0;
        for (String s : list) {
            Order order = orderMap.get(s);
            deliveryTime = Math.max(deliveryTime, order.getDeliveryTime());
        }
        int hour = deliveryTime / 60;
        String sHour = "";
        if (hour < 10) {
            sHour = "0" + String.valueOf(hour);
        } else {
            sHour = String.valueOf(hour);
        }

        int min = deliveryTime % 60;
        String sMin = "";
        if (min < 10) {
            sMin = "0" + String.valueOf(min);
        } else {
            sMin = String.valueOf(min);
        }

        time = sHour + ":" + sMin;

        return time;

    }

    public String deletePartnerById(String partnerId) {
        partnerMap.remove(partnerId);

        List<String> list = pairMap.getOrDefault(partnerId, new ArrayList<>());
        ListIterator<String> itr = list.listIterator();
        while (itr.hasNext()) {
            String s = itr.next();
            assignedMap.remove(s);
        }
        pairMap.remove(partnerId);
        return "Deleted";
    }

    public String deleteOrderById(String orderId) {
        orderMap.remove(orderId);
        String partnerId = assignedMap.get(orderId);
        assignedMap.remove(orderId);
        List<String> list = pairMap.get(partnerId);

        ListIterator<String> itr = list.listIterator();
        while (itr.hasNext()) {
            String s = itr.next();
            if (s.equals(orderId)) {
                itr.remove();
            }
        }
        pairMap.put(partnerId, list);
        return "Deleted";

    }
}