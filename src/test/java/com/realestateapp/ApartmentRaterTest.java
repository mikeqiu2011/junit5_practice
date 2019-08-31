package com.realestateapp;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApartmentRaterTest {
    private RandomApartmentGenerator randomApartmentGenerator;

    @BeforeEach
    void setUp() {
        System.out.println("one unit test started");
        this.randomApartmentGenerator = new RandomApartmentGenerator();
    }

    @AfterEach
    void tearDown() {
        System.out.println("one unit test completed");
    }

    @Nested
    class RateApartmentTests {
        @ParameterizedTest
        @CsvFileSource(resources = "/apartments_rating.csv",numLinesToSkip = 1)
        void should_ReturnCorrectRating_when_CorrectDepartment(double area, BigDecimal price, int rate){
            //given
            Apartment apartment = new Apartment(area,price);
            int expect = rate;

            //when
            int actual = ApartmentRater.rateApartment(apartment);

            //then
            assertEquals(expect, actual);
        }



        @Test
        void should_ReturnErrorValue_when_IncorrectAppartment(){
            //given
            Apartment apartment = new Apartment(0,new BigDecimal(100000));
            int expect = -1;

            //when
            int actual = ApartmentRater.rateApartment(apartment);

            //then
            assertEquals(expect, actual);
        }

    }

    @Nested
    class CalculateAverageRatingTests {

        @Test
        void should_ThrowRunTimeException_when_ListIsEmpty(){
            //given
            List<Apartment> apartments = new ArrayList<Apartment>();

            //when
            Executable executable = () -> ApartmentRater.calculateAverageRating(apartments);

            //then
            assertThrows(RuntimeException.class, executable);
        }

        @Test
        void should_CalculateAverageRating_When_CorrectApartmentList() {

            // given
            List<Apartment> apartments = new ArrayList<>();
            apartments.add(new Apartment(72.0, new BigDecimal(250000.0)));
            apartments.add(new Apartment(48.0, new BigDecimal(350000.0)));
            apartments.add(new Apartment(30.0, new BigDecimal(600000.0)));

            double expected = 1.0;

            // when
            double actual = ApartmentRater.calculateAverageRating(apartments);

            // then
            assertEquals(expected, actual);
        }

        //performance test for a particular method
        @Test
        void should_ReturnRunWithIn500Ms_when_1000ListItemsIsGiven(){
            //given
            List<Apartment> apartments = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                apartments.add(ApartmentRaterTest.this.randomApartmentGenerator.generate());
            }

            //when
            Executable executable = () -> ApartmentRater.calculateAverageRating(apartments);

            assertTimeout(Duration.ofMillis(500), executable);

        }

    }
}