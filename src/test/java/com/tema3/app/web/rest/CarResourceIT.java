package com.tema3.app.web.rest;

import com.tema3.app.Tema3SpringApp;
import com.tema3.app.domain.Car;
import com.tema3.app.repository.CarRepository;
import com.tema3.app.service.CarService;
import com.tema3.app.service.UserService;
import com.tema3.app.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.tema3.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tema3.app.domain.enumeration.FuelType;
/**
 * Integration tests for the {@link CarResource} REST controller.
 */
@SpringBootTest(classes = Tema3SpringApp.class)
public class CarResourceIT {

    private static final String DEFAULT_LICENSE_PLATE = "AAAAAAAAAA";
    private static final String UPDATED_LICENSE_PLATE = "BBBBBBBBBB";

    private static final String DEFAULT_BRAND = "AAAAAAAAAA";
    private static final String UPDATED_BRAND = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRODUCTION_YEAR = 1;
    private static final Integer UPDATED_PRODUCTION_YEAR = 2;

    private static final Integer DEFAULT_ENGINE_SIZE = 1;
    private static final Integer UPDATED_ENGINE_SIZE = 2;

    private static final FuelType DEFAULT_FUEL = FuelType.GASOLINE;
    private static final FuelType UPDATED_FUEL = FuelType.DIESEL;

    private static final Integer DEFAULT_ENGINE_POWER = 1;
    private static final Integer UPDATED_ENGINE_POWER = 2;

    private static final Integer DEFAULT_ENGINE_TORQUE = 1;
    private static final Integer UPDATED_ENGINE_TORQUE = 2;

    private static final Integer DEFAULT_TRUNK_SIZE = 1;
    private static final Integer UPDATED_TRUNK_SIZE = 2;

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarService carService;

    @Autowired
    private UserService userService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCarMockMvc;

    private Car car;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CarResource carResource = new CarResource(carService, userService);
        this.restCarMockMvc = MockMvcBuilders.standaloneSetup(carResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Car createEntity(EntityManager em) {
        Car car = new Car()
            .licensePlate(DEFAULT_LICENSE_PLATE)
            .brand(DEFAULT_BRAND)
            .model(DEFAULT_MODEL)
            .productionYear(DEFAULT_PRODUCTION_YEAR)
            .engineSize(DEFAULT_ENGINE_SIZE)
            .fuel(DEFAULT_FUEL)
            .enginePower(DEFAULT_ENGINE_POWER)
            .engineTorque(DEFAULT_ENGINE_TORQUE)
            .trunkSize(DEFAULT_TRUNK_SIZE)
            .price(DEFAULT_PRICE);
        return car;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Car createUpdatedEntity(EntityManager em) {
        Car car = new Car()
            .licensePlate(UPDATED_LICENSE_PLATE)
            .brand(UPDATED_BRAND)
            .model(UPDATED_MODEL)
            .productionYear(UPDATED_PRODUCTION_YEAR)
            .engineSize(UPDATED_ENGINE_SIZE)
            .fuel(UPDATED_FUEL)
            .enginePower(UPDATED_ENGINE_POWER)
            .engineTorque(UPDATED_ENGINE_TORQUE)
            .trunkSize(UPDATED_TRUNK_SIZE)
            .price(UPDATED_PRICE);
        return car;
    }

    @BeforeEach
    public void initTest() {
        car = createEntity(em);
    }

    @Test
    @Transactional
    public void createCar() throws Exception {
        int databaseSizeBeforeCreate = carRepository.findAll().size();

        // Create the Car
        restCarMockMvc.perform(post("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car)))
            .andExpect(status().isCreated());

        // Validate the Car in the database
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeCreate + 1);
        Car testCar = carList.get(carList.size() - 1);
        assertThat(testCar.getLicensePlate()).isEqualTo(DEFAULT_LICENSE_PLATE);
        assertThat(testCar.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testCar.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testCar.getProductionYear()).isEqualTo(DEFAULT_PRODUCTION_YEAR);
        assertThat(testCar.getEngineSize()).isEqualTo(DEFAULT_ENGINE_SIZE);
        assertThat(testCar.getFuel()).isEqualTo(DEFAULT_FUEL);
        assertThat(testCar.getEnginePower()).isEqualTo(DEFAULT_ENGINE_POWER);
        assertThat(testCar.getEngineTorque()).isEqualTo(DEFAULT_ENGINE_TORQUE);
        assertThat(testCar.getTrunkSize()).isEqualTo(DEFAULT_TRUNK_SIZE);
        assertThat(testCar.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createCarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carRepository.findAll().size();

        // Create the Car with an existing ID
        car.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarMockMvc.perform(post("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car)))
            .andExpect(status().isBadRequest());

        // Validate the Car in the database
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLicensePlateIsRequired() throws Exception {
        int databaseSizeBeforeTest = carRepository.findAll().size();
        // set the field null
        car.setLicensePlate(null);

        // Create the Car, which fails.

        restCarMockMvc.perform(post("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car)))
            .andExpect(status().isBadRequest());

        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBrandIsRequired() throws Exception {
        int databaseSizeBeforeTest = carRepository.findAll().size();
        // set the field null
        car.setBrand(null);

        // Create the Car, which fails.

        restCarMockMvc.perform(post("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car)))
            .andExpect(status().isBadRequest());

        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProductionYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = carRepository.findAll().size();
        // set the field null
        car.setProductionYear(null);

        // Create the Car, which fails.

        restCarMockMvc.perform(post("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car)))
            .andExpect(status().isBadRequest());

        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEngineSizeIsRequired() throws Exception {
        int databaseSizeBeforeTest = carRepository.findAll().size();
        // set the field null
        car.setEngineSize(null);

        // Create the Car, which fails.

        restCarMockMvc.perform(post("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car)))
            .andExpect(status().isBadRequest());

        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCars() throws Exception {
        // Initialize the database
        carRepository.saveAndFlush(car);

        // Get all the carList
        restCarMockMvc.perform(get("/api/cars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(car.getId().intValue())))
            .andExpect(jsonPath("$.[*].licensePlate").value(hasItem(DEFAULT_LICENSE_PLATE)))
            .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].productionYear").value(hasItem(DEFAULT_PRODUCTION_YEAR)))
            .andExpect(jsonPath("$.[*].engineSize").value(hasItem(DEFAULT_ENGINE_SIZE)))
            .andExpect(jsonPath("$.[*].fuel").value(hasItem(DEFAULT_FUEL.toString())))
            .andExpect(jsonPath("$.[*].enginePower").value(hasItem(DEFAULT_ENGINE_POWER)))
            .andExpect(jsonPath("$.[*].engineTorque").value(hasItem(DEFAULT_ENGINE_TORQUE)))
            .andExpect(jsonPath("$.[*].trunkSize").value(hasItem(DEFAULT_TRUNK_SIZE)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)));
    }

    @Test
    @Transactional
    public void getCar() throws Exception {
        // Initialize the database
        carRepository.saveAndFlush(car);

        // Get the car
        restCarMockMvc.perform(get("/api/cars/{id}", car.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(car.getId().intValue()))
            .andExpect(jsonPath("$.licensePlate").value(DEFAULT_LICENSE_PLATE))
            .andExpect(jsonPath("$.brand").value(DEFAULT_BRAND))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.productionYear").value(DEFAULT_PRODUCTION_YEAR))
            .andExpect(jsonPath("$.engineSize").value(DEFAULT_ENGINE_SIZE))
            .andExpect(jsonPath("$.fuel").value(DEFAULT_FUEL.toString()))
            .andExpect(jsonPath("$.enginePower").value(DEFAULT_ENGINE_POWER))
            .andExpect(jsonPath("$.engineTorque").value(DEFAULT_ENGINE_TORQUE))
            .andExpect(jsonPath("$.trunkSize").value(DEFAULT_TRUNK_SIZE))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE));
    }

    @Test
    @Transactional
    public void getNonExistingCar() throws Exception {
        // Get the car
        restCarMockMvc.perform(get("/api/cars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCar() throws Exception {
        // Initialize the database
        carService.save(car);

        int databaseSizeBeforeUpdate = carRepository.findAll().size();

        // Update the car
        Car updatedCar = carRepository.findById(car.getId()).get();
        // Disconnect from session so that the updates on updatedCar are not directly saved in db
        em.detach(updatedCar);
        updatedCar
            .licensePlate(UPDATED_LICENSE_PLATE)
            .brand(UPDATED_BRAND)
            .model(UPDATED_MODEL)
            .productionYear(UPDATED_PRODUCTION_YEAR)
            .engineSize(UPDATED_ENGINE_SIZE)
            .fuel(UPDATED_FUEL)
            .enginePower(UPDATED_ENGINE_POWER)
            .engineTorque(UPDATED_ENGINE_TORQUE)
            .trunkSize(UPDATED_TRUNK_SIZE)
            .price(UPDATED_PRICE);

        restCarMockMvc.perform(put("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCar)))
            .andExpect(status().isOk());

        // Validate the Car in the database
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeUpdate);
        Car testCar = carList.get(carList.size() - 1);
        assertThat(testCar.getLicensePlate()).isEqualTo(UPDATED_LICENSE_PLATE);
        assertThat(testCar.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testCar.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testCar.getProductionYear()).isEqualTo(UPDATED_PRODUCTION_YEAR);
        assertThat(testCar.getEngineSize()).isEqualTo(UPDATED_ENGINE_SIZE);
        assertThat(testCar.getFuel()).isEqualTo(UPDATED_FUEL);
        assertThat(testCar.getEnginePower()).isEqualTo(UPDATED_ENGINE_POWER);
        assertThat(testCar.getEngineTorque()).isEqualTo(UPDATED_ENGINE_TORQUE);
        assertThat(testCar.getTrunkSize()).isEqualTo(UPDATED_TRUNK_SIZE);
        assertThat(testCar.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingCar() throws Exception {
        int databaseSizeBeforeUpdate = carRepository.findAll().size();

        // Create the Car

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarMockMvc.perform(put("/api/cars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(car)))
            .andExpect(status().isBadRequest());

        // Validate the Car in the database
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCar() throws Exception {
        // Initialize the database
        carService.save(car);

        int databaseSizeBeforeDelete = carRepository.findAll().size();

        // Delete the car
        restCarMockMvc.perform(delete("/api/cars/{id}", car.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Car> carList = carRepository.findAll();
        assertThat(carList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
