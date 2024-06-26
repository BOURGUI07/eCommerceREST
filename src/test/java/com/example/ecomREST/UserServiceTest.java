/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.example.ecomREST;

import java.util.Arrays;
import java.util.Optional;
import main.dto.ProfileDTO;
import main.dto.UserDTO;
import main.entity.Profile;
import main.entity.User;
import main.mapper.ProfileMapper;
import main.mapper.UserMapper;
import main.repo.OrderRepo;
import main.repo.ProfileRepo;
import main.repo.UserRepo;
import main.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author hp
 */
public class UserServiceTest {
    @Mock
    private UserMapper userMapper;
    @Mock
    private ProfileMapper profileMapper;
    @Mock
    private OrderRepo orderRepo;
    @Mock
    private UserRepo userRepo;
    @Mock
    private ProfileRepo profileRepo;
    @InjectMocks
    private UserService service;
    private User u;
    private UserDTO ux;
    private UserDTO ux2;
    private Profile p;
    private  ProfileDTO px;
    private ProfileDTO px2;
    
    public UserServiceTest() {
        p = new Profile();
        p.setAddress("address");
        p.setEmail("email");
        p.setId(1);
        p.setPhone("0606");
        
        px = new ProfileDTO();
        px.setAddress("address");
        px.setEmail("email");
        px.setId(1);
        px.setPhone("0606");
        
        px2 = new ProfileDTO();
        px2.setAddress("add");
        px2.setEmail("emai");
        px2.setId(1);
        px2.setPhone("0707");
        
        u = new User();
        u.setId(1);
        u.setName("youness");
        u.setProfile(p);
        
        ux = new UserDTO();
        ux.setId(1);
        ux.setName("youness");
        ux.setProfileId(1);
        
        ux2 = new UserDTO();
        ux2.setId(1);
        ux2.setName("yassine");
        ux2.setProfileId(1);
        
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    @Test
    public void testCreateProfile(){
        when(profileMapper.toProfile(px)).thenReturn(p);
        when(profileRepo.save(p)).thenReturn(p);
        when(profileMapper.toDTO(p)).thenReturn(px);
        
        var result = this.service.createProfile(px);
        assertEquals(px, result);
        verify(profileRepo, times(1)).save(p);
        verify(profileMapper,times(1)).toProfile(px);
        verify(profileMapper,times(1)).toDTO(p);
    }
    
    @Test
    public void testFindProfile(){
        when(profileRepo.findById(1)).thenReturn(Optional.of(p));
        when(profileMapper.toDTO(p)).thenReturn(px);
        
        var result = this.service.findProfileById(1);
        assertEquals(px, result);
        verify(profileRepo, times(1)).findById(1);
        verify(profileMapper, times(1)).toDTO(p);
    }
    
    @Test
    public void testUpdateProfile(){
        when(profileRepo.findById(1)).thenReturn(Optional.of(p));
        when(profileRepo.save(p)).thenReturn(p);
        when(profileMapper.toDTO(p)).thenReturn(px2);
        var result = this.service.updateProfile(1, px2);
        assertEquals(px2, result);
        verify(profileRepo,times(1)).findById(1);
        verify(profileRepo, times(1)).save(p);
        verify(profileMapper, times(1)).toDTO(p);
    }
    
    @Test
    public void testFindAllPofiles(){
        when(profileRepo.findAll()).thenReturn(Arrays.asList(p));
        when(profileMapper.toDTO(p)).thenReturn(px2);
        var result = this.service.getAllProfiles();
        assertEquals(1, result.size());
    }
    
    @Test
    public void testCreateUser(){
        when(userMapper.toUser(ux)).thenReturn(u);
        when(userRepo.save(u)).thenReturn(u);
        when(userMapper.toDTO(u)).thenReturn(ux);
        
        var result = this.service.createUser(ux);
        assertEquals(ux, result);
        assertNotNull(u.getProfile());
        verify(userMapper, times(1)).toUser(ux);
        verify(userRepo, times(1)).save(u);
        verify(userMapper, times(1)).toDTO(u);
    }
    
    @Test
    public void testFindUser(){
        when(userRepo.findById(1)).thenReturn(Optional.of(u));
        when(userMapper.toDTO(u)).thenReturn(ux);
        
        var result = this.service.findUserById(1);
        assertEquals(ux, result);
        verify(userRepo, times(1)).findById(1);
        verify(userMapper,times(1)).toDTO(u);
    }
    
    @Test 
    public void testUpdateUser(){
        when(userRepo.findById(1)).thenReturn(Optional.of(u));
        when(userRepo.save(u)).thenReturn(u);
        when(userMapper.toDTO(u)).thenReturn(ux2);
        var result = this.service.updateUser(1, ux2);
        assertEquals(ux2,result);
    }
    
    @Test
    public void testDeleteUser(){
        doNothing().when(userRepo).deleteById(1);
        this.service.deleteUser(1);
        verify(userRepo,times(1)).deleteById(1);
    }
    
    @Test
    public void testFindAllUsers(){
        when(userRepo.findAll()).thenReturn(Arrays.asList(u));
        when(userMapper.toDTO(u)).thenReturn(ux);
        var size = this.service.getAllUsers().size();
        assertEquals(1,size);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
