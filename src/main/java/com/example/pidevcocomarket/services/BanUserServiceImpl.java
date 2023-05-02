package com.example.pidevcocomarket.services;

import com.example.pidevcocomarket.entities.BanUser;
import com.example.pidevcocomarket.entities.User;
import com.example.pidevcocomarket.interfaces.IBanUserService;
import com.example.pidevcocomarket.repositories.BanUserRepository;
import com.example.pidevcocomarket.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BanUserServiceImpl implements IBanUserService {
    @Autowired
    private BanUserRepository banRepository;

    public BanUser createBan(User user, String reason, String startDate, String endDate) {
        BanUser ban = new BanUser();
        ban.setUser(user);
        ban.setReason(reason);
        ban.setStartDate(startDate);
        ban.setEndDate(endDate);
        return banRepository.save(ban);
    }

    public List<BanUser> getBansByUser(User user) {
        return banRepository.findByUser(user);
    }

    public void deleteBan(BanUser ban) {
        banRepository.delete(ban);
    }

    @Override
    public BanUser getBanById(Integer banId) {
        Optional<BanUser> banOptional = banRepository.findById(banId);
        if (banOptional.isPresent()) {
            return banOptional.get();
        } else {
            return null;
        }
    }


}
