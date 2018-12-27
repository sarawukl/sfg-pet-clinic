package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    public static final String LAST_NAME = "smith";
    Owner returnOwner;

    @Mock
    OwnerRepository ownerRepository;

    @Mock
    PetRepository petRepository;

    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerSDJpaService service;

    @BeforeEach
    void setUp() {
        returnOwner = Owner.builder().id(1L).lastName(LAST_NAME).build();
    }

    @Test
    void findByLastName() {

        //arrange
        when(ownerRepository.findByLastName(any())).thenReturn(returnOwner);

        //action
        Owner smith = service.findByLastName(LAST_NAME);

        //assert
        assertEquals(LAST_NAME, smith.getLastName());
        verify(ownerRepository).findByLastName(any());
    }

    @Test
    void findAll() {

        //arrange
        Set<Owner> returnOwners = new HashSet<>();
        returnOwners.add(Owner.builder().id(1L).build());
        returnOwners.add(Owner.builder().id(2L).build());
        when(ownerRepository.findAll()).thenReturn(returnOwners);

        //action
        Set<Owner> owners = new HashSet<>();
        service.findAll().forEach(owners::add);

        //assert
        assertEquals(2, owners.size());
        verify(ownerRepository).findAll();

    }

    @Test
    void findById() {

        //arrange
        when(ownerRepository.findById(any())).thenReturn(Optional.of(returnOwner));

        //action
        Owner owner = service.findById(1L);

        //assert
        assertEquals(LAST_NAME, owner.getLastName());
        verify(ownerRepository).findById(any());

    }

    @Test
    void findByIdNotFound() {

        //arrange
        when(ownerRepository.findById(any())).thenReturn(Optional.empty());

        //action
        Owner owner = service.findById(1L);

        //assert
        assertNull(owner);
        verify(ownerRepository).findById(any());

    }

    @Test
    void save() {

        //arrange
        Owner ownerToSave = Owner.builder().id(1L).build();
        when(ownerRepository.save(any())).thenReturn(returnOwner);

        //action
        Owner savedOwner = service.save(Owner.builder().id(1L).build());

        //assert
        assertNotNull(savedOwner);
        verify(ownerRepository).save(any());
    }

    @Test
    void delete() {

        //action
        service.delete(returnOwner);

        //assert
        verify(ownerRepository).delete(any());
    }

    @Test
    void deleteById() {

        //action
        service.deleteById(1L);

        //assert
        verify(ownerRepository).deleteById(any());
    }
}