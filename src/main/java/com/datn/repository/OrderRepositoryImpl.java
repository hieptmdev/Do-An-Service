package com.datn.repository;

import com.datn.dto.OderDTO;
import com.datn.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.*;

@Transactional
public class OrderRepositoryImpl implements OrderRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    @Override
    public Map<String, Double> getChartDateByYear(Long year) {
        Map<String, Double> chartData = new HashMap<>();
        try {
            String sql = "select" +
                    "  month(created_date) as `month`," +
                    "  sum(total_result) as result" +
                    " from _order" +
                    " where `status` = 2 and year(created_date) = ?" +
                    " group by month(created_date)";
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter(1, year);
            List lst = query.getResultList();
            if (lst == null || lst.isEmpty() || lst.size() == 0) {
                for (int month = 1; month <= 12; month++) {
                    chartData.put("thang" + month, 0d);
                }
            } else {
                List<Object[]> data = lst;
                for (int month = 1; month <= 12; month++) {
                    int finalMonth = month;
                    Object[] obj = data.stream().filter(objects -> (int) objects[0] == finalMonth).findAny().orElse(null);
                    if (obj == null) {
                        chartData.put("thang" + month, 0d);
                    } else {
                        chartData.put("thang" + month, Double.valueOf(obj[1].toString()));
                    }
                }
            }
            return chartData;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Order> dowloadOrder(OderDTO dto) {
        String sql = "select o.code, o.usernameKH, o.code ,o.totalResult,o.numberProduct, o.createdDate, o.deliveryAddress,o.phoneNumber from Order o where 1 = 1";
        StringBuilder sb = new StringBuilder();
        sb.append(sql);
        if (dto.getCode() != null && !"".equals(dto.getCode())) {
            sb.append(" and o.code like concat('%',:code,'%')");
        }
        Query query = entityManager.createQuery(sb.toString());
        if (dto.getCode() != null && !"".equals(dto.getCode())) {
            query.setParameter("code", dto.getCode());
        }
        List lst = query.getResultList();
        List<Order> orderList = null;
        if (lst != null || !lst.isEmpty()) {
            orderList = new ArrayList<>();
            for (Object data : lst) {
                Object[] obj = (Object[]) data;
                Order order = new Order();
                order.setCode((String) obj[0]);
                order.setUsernameKH((String) obj[1]);
                order.setCode((String) obj[2]);
                order.setTotalResult((Long) obj[3]);
                order.setNumberProduct((Long) obj[4]);
                order.setCreatedDate((Date) obj[5]);
                order.setDeliveryAddress((String) obj[6]);
                order.setPhoneNumber((String) obj[7]);
                orderList.add(order);
            }
        }
        return orderList;
    }

    @Override
    public List<Order> findByCode(Integer status) {
        String sql = "select o.id, o.code, o.totalResult,o.numberProduct, o.createdDate, o.deliveryDate, o.deliveryAddress,o.phoneNumber,o.usernameKH,o.status from Order o where 1 = 1";
        StringBuilder sb = new StringBuilder();
        sb.append(sql);
        if (status != null) {
            sb.append(" and o.status = :status");
        }

        Query query = entityManager.createQuery(sb.toString());
        if (status != null) {
            query.setParameter("status", status);
        }
        List lst = query.getResultList();
        List<Order> orderList = null;
        if (lst != null || !lst.isEmpty()) {
            orderList = new ArrayList<>();
            for (Object data : lst) {
                Object[] obj = (Object[]) data;
                Order order = new Order();
                order.setId((Long) obj[0]);
                order.setCode((String) obj[1]);
                order.setTotalResult((Long) obj[2]);
                order.setNumberProduct((Long) obj[3]);
                order.setCreatedDate((Date) obj[4]);
                order.setDeliveryDate((Date) obj[5]);
                order.setDeliveryAddress((String) obj[6]);
                order.setPhoneNumber((String) obj[7]);
                order.setUsernameKH((String) obj[8]);
                order.setStatus((Integer) obj[9]);
                orderList.add(order);
            }
        }
        return orderList;
    }
}
