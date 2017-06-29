package com.etna.Dao;

import com.etna.Entity.Offer;
import com.etna.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by quentin on 29/06/2017.
 */

@Repository
public class OfferDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static class OfferRowMapper implements RowMapper<Offer> {
        @Override
        public Offer mapRow(ResultSet resultSet, int i) throws SQLException {
            Offer offer = new Offer();
            offer.setId(resultSet.getInt("id"));
            offer.setAuthor(resultSet.getString("author"));
            offer.setUuid(resultSet.getString("uuid"));
            offer.setTitle(resultSet.getString("title"));
            offer.setContent(resultSet.getString("content"));
            return offer;
        }
    }

    public Collection<Offer> getAllOffers() {
        // SELECT columns FROM table
        final String sql = "SELECT id, uuid, author, title, content FROM Offers";
        Collection<Offer> offers = jdbcTemplate.query(sql, new OfferRowMapper());
        return offers;
    }

    public Offer getOfferById(Integer id) {
        // SELECT columns FROM table WHERE column = value
        final String sql = "SELECT id, uuid, author, title, content FROM Offers WHERE id = ?";
        Offer offer = jdbcTemplate.queryForObject(sql, new OfferRowMapper(), id);
        return offer;
    }

    public void removeOfferById(int id) {
        final String sql = "DELETE FROM Offers WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void updateOffer(Offer offer) {
        final String sql = "UPDATE title = ?, content = ? WHERE id = ? FROM Offers";
        jdbcTemplate.update(sql, new Object[] {offer.getTitle(), offer.getContent(), offer.getId()});
    }

    public void insertOffer(Offer offer) {
        //this.offers.put(offer.getId(), offer);
    }
}
