package com.pawszo.keyboardking.dev.mapper;


import com.pawszo.keyboardking.dev.dto.CreateUserDTO;
import com.pawszo.keyboardking.dev.model.User;
import org.junit.Assert;
import org.junit.BeforeClass;

public class UserMapperTest {

    private CreateUserDTO inputUser;
    private User createdUser;

    @BeforeClass
    public void createData() {
        //todo fixme create mocks
        //inputUser = new CreateUserDTO("junitUser1", "junit@test.com", "password")
    }

    public void CreateUserDTOMapsToUser_returnsTrue() {
        Assert.assertTrue(true);
    }
}
