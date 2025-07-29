package com.example.webhookapp.service;

import org.springframework.stereotype.Service;

@Service
public class QueryService {
    
    public String buildQuery(String regNo) {
        int lastDigits = Integer.parseInt(regNo.substring(regNo.length() - 2));
        return lastDigits % 2 == 1 ? getOddQuery() : getEvenQuery();
    }
    
    private String getOddQuery() {
        return "SELECT e.department_id, d.department_name, COUNT(e.employee_id) as employee_count, " +
               "AVG(e.salary) as avg_salary FROM employees e " +
               "INNER JOIN departments d ON e.department_id = d.department_id " +
               "WHERE e.hire_date >= '2020-01-01' GROUP BY e.department_id, d.department_name " +
               "HAVING COUNT(e.employee_id) > 5 ORDER BY avg_salary DESC";
    }
    
    private String getEvenQuery() {
        return "WITH sales_summary AS (SELECT product_id, customer_id, SUM(quantity * price) as total_sales, " +
               "ROW_NUMBER() OVER (PARTITION BY product_id ORDER BY SUM(quantity * price) DESC) as rn " +
               "FROM orders o JOIN order_items oi ON o.order_id = oi.order_id " +
               "WHERE o.order_date BETWEEN '2023-01-01' AND '2023-12-31' GROUP BY product_id, customer_id) " +
               "SELECT p.product_name, c.customer_name, ss.total_sales FROM sales_summary ss " +
               "JOIN products p ON ss.product_id = p.product_id " +
               "JOIN customers c ON ss.customer_id = c.customer_id " +
               "WHERE ss.rn = 1 ORDER BY ss.total_sales DESC";
    }
}