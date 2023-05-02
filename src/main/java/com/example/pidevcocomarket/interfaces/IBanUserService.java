package com.example.pidevcocomarket.interfaces;

import com.example.pidevcocomarket.entities.BanUser;
import com.example.pidevcocomarket.entities.User;

import java.time.LocalDateTime;
import java.util.List;

public interface IBanUserService {
    public BanUser createBan(User user, String reason, String startDate, String endDate);
    public List<BanUser> getBansByUser(User user);
    public void deleteBan(BanUser ban) ;
    BanUser getBanById(Integer banId);
}
