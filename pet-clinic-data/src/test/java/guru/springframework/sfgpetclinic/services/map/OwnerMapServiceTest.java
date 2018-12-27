package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

    OwnerMapService ownerMapService;
    final Long ownerId = 1L;
    final String lastName = "someLastName";

    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());
        ownerMapService.save(Owner.builder().id(ownerId).lastName(lastName).build());
    }

    @Test
    void findAll() {

        //action
        Set<Owner> owners = ownerMapService.findAll();

        //assert
        assertEquals(1, owners.size());
    }

    @Test
    void findById() {

        //action
        Owner owner = ownerMapService.findById(ownerId);

        //assert
        assertEquals(ownerId, owner.getId());
    }

    @Test
    void saveExistingId() {

        //arrange
        Long id = 2L;
        Owner owner2 = Owner.builder().id(id).build();

        //action
        Owner savedOwner = ownerMapService.save(owner2);

        //assert
        assertEquals(id, savedOwner.getId());
    }

    @Test
    void saveNoId() {

        //action
        Owner savedOwner = ownerMapService.save(Owner.builder().build());

        //assert
        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void delete() {

        //action
        ownerMapService.delete(ownerMapService.findById(ownerId));

        //assert
        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void deleteById() {

        //action
        ownerMapService.deleteById(ownerId);

        //assertion
        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void findByLastName() {

        //action
        Owner owner = ownerMapService.findByLastName(lastName);

        //assert
        assertNotNull(owner);
        assertEquals(lastName, owner.getLastName());
    }

    @Test
    void findByLastNameNotFound() {

        //action
        Owner owner = ownerMapService.findByLastName("foo");

        //assert
        assertNull(owner);
    }
}