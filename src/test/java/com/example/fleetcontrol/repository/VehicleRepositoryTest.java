package com.example.fleetcontrol.repository;

import com.example.fleetcontrol.model.Role;
import com.example.fleetcontrol.model.User;
import com.example.fleetcontrol.model.Vehicle;
import com.example.fleetcontrol.model.VehicleStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class VehicleRepositoryTest {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired 
    private UserRepository userRepository; 

    @Test
    void findByOwnerIdAndStatus_returnsOnlyMatchingVehicles() {
        User owner = saveUser("owner@test.com");
        User other = saveUser("other@test.com");
        
        vehicleRepository.save(newVehicle("1111AAA", VehicleStatus.AVAILABLE, owner));
        vehicleRepository.save(newVehicle("2222BBB", VehicleStatus.IN_USE, owner));
        vehicleRepository.save(newVehicle("3333CCC", VehicleStatus.IN_USE, other));

        Page<Vehicle> result = vehicleRepository.findByOwnerIdAndStatus(owner.getId() , VehicleStatus.AVAILABLE, PageRequest.of(0,10));

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getPlate()).isEqualTo("1111AAA");

    }

    @Test
    void findByIdAndOwnerId_doesNotReturnVehicleOfAnotherUser(){
        User owner = saveUser("owner@test.com");
        User other = saveUser("other@test.com");

        Vehicle vehicle = vehicleRepository.save(newVehicle("1111AAA", VehicleStatus.AVAILABLE, owner));

        assertThat(vehicleRepository.findByIdAndOwnerId(vehicle.getId(), owner.getId() )).isPresent();
        assertThat(vehicleRepository.findByIdAndOwnerId(vehicle.getId(), other.getId() )).isEmpty();


    }

    private User saveUser(String email) {
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash("hach");
        user.setRole(Role.USER);
        return userRepository.save(user);

    }

    private Vehicle newVehicle(String plate, VehicleStatus status, User owner){
        Vehicle vehicle = new Vehicle();
        vehicle.setPlate(plate);
        vehicle.setBrand("Totota");
        vehicle.setModel("Corolla");
        vehicle.setYear(2020);
        vehicle.setMileage(10000);
        vehicle.setStatus(status);
        vehicle.setOwner(owner);
        return vehicle;
    }



}
