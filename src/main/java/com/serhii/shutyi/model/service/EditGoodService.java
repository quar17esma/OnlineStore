package com.serhii.shutyi.model.service;

import com.serhii.shutyi.dao.DaoFactory;
import com.serhii.shutyi.dao.GoodDAO;
import com.serhii.shutyi.model.entity.Good;

import java.util.Optional;

public class EditGoodService {
    DaoFactory factory = DaoFactory.getInstance();

    private static class Holder {
        private static EditGoodService INSTANCE = new EditGoodService();
    }

    public static EditGoodService getInstance() {
        return EditGoodService.Holder.INSTANCE;
    }

    public Good getGoodById(int goodId) {
        Good good = null;
        try(GoodDAO goodDAO = factory.createGoodDAO()) {
            good = goodDAO.findById(goodId).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return good;
    }
}
