package org.example.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.reggie.entity.Employee;
import org.example.reggie.mapper.EmployeeMapper;
import org.example.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService, UserDetailsService {

    private static final String DEFAULT_PASSWORD = "123456";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getUsername, username);
        UserDetails user = this.getOne(wrapper);
        if (user == null) {
            throw new UsernameNotFoundException("");
        }
        return user;
    }

    @Override
    public Page<Employee> pageWithNameOrderByUpdateTime(Long pageNum, Long pageSize ,String name) {
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null, Employee::getName, name);
        wrapper.orderByDesc(Employee::getUpdateTime);
        return this.page(new Page<>(pageNum, pageSize), wrapper);
    }

    @Override
    public Employee saveWithDefaultPassword(Employee employee) {
        employee.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
        this.save(employee);
        return employee;
    }
}
