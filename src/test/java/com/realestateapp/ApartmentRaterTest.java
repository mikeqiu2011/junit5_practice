package com.realestateapp;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ApartmentRaterTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Nested
    class RateApartmentTests {
        @Test
        void should_ReturnZero_when_RateAsCheap(){
            //given
            Apartment apartment = new Apartment(100,new BigDecimal(3000000));

            //when
            int actual = ApartmentRater.rateApartment(apartment);

            //then
            assertEquals(0, actual);
        }
    }

    @Nested
    class CalculateAverageRatingTests {
    }
}