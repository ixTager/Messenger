package com.anonchat.anonymousmessenger.unit;

import com.anonchat.anonymousmessenger.dto.UserDTO;
import com.anonchat.anonymousmessenger.model.User;
import com.anonchat.anonymousmessenger.model.UserProfile;
import com.anonchat.anonymousmessenger.utils.UserUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class UserUtilTest {
    @InjectMocks
    private UserUtil userUtil;

    @Test
    public void toUserDTO_ByUser_ReturnUserDTO() {
        // Arrange
        UserProfile userProfile = UserProfile.builder()
                .id(10L)
                .firstName("firstName")
                .lastName("lastName")
                .build();

        User user = User.builder()
                .id(10L)
                .profile(userProfile)
                .uniqueUserId("uniqueUserId")
                .build();
        UserDTO userDTO = userUtil.toUserDTO(user);

        assertNotNull(userDTO);
        assertEquals(userDTO.getFirstName(), userProfile.getFirstName());
        assertEquals(userDTO.getLastName(), userProfile.getLastName());
        assertEquals(userDTO.getUniqueUserId(), user.getUniqueUserId());
    }
}
