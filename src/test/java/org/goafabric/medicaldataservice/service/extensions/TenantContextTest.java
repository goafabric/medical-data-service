package org.goafabric.medicaldataservice.service.extensions;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Base64;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class TenantContextTest {

    @Mock
    private HttpServletRequest request;

    @AfterEach
    void tearDown() {
        TenantContext.removeContext();
    }

    @Test
    void shouldSetContextFromMap() {
        // Given
        Map<String, String> tenantHeaderMap = Map.of(
                "X-TenantId", "123",
                "X-OrganizationId", "456",
                "X-Auth-Request-Preferred-Username", "testuser"
        );

        // When
        TenantContext.setContext(tenantHeaderMap);

        // Then
        assertThat(TenantContext.getTenantId()).isEqualTo("123");
        assertThat(TenantContext.getOrganizationId()).isEqualTo("456");
        assertThat(TenantContext.getUserName()).isEqualTo("testuser");
    }

    @Test
    void shouldSetContextFromHttpRequest() {
        // Given
        lenient().when(request.getHeader("X-TenantId")).thenReturn("123");
        lenient().when(request.getHeader("X-OrganizationId")).thenReturn("456");
        lenient().when(request.getHeader("X-Auth-Request-Preferred-Username")).thenReturn("testuser");

        // When
        TenantContext.setContext(request);

        // Then
        assertThat(TenantContext.getTenantId()).isEqualTo("123");
        assertThat(TenantContext.getOrganizationId()).isEqualTo("456");
        assertThat(TenantContext.getUserName()).isEqualTo("testuser");
    }

    @Test
    void shouldSetContextWithDefaultValuesWhenNull() {
        // Given
        Map<String, String> tenantHeaderMap = Map.of();

        // When
        TenantContext.setContext(tenantHeaderMap);

        // Then
        assertThat(TenantContext.getTenantId()).isEqualTo("0");
        assertThat(TenantContext.getOrganizationId()).isEqualTo("0");
        assertThat(TenantContext.getUserName()).isEqualTo("anonymous");
    }

    @Test
    void shouldExtractUserNameFromUserInfo() {
        // Given
        String userInfo = Base64.getUrlEncoder().encodeToString("{\"preferred_username\":\"extracted_user\"}".getBytes());
        lenient().when(request.getHeader("X-TenantId")).thenReturn("123");
        lenient().when(request.getHeader("X-OrganizationId")).thenReturn("456");
        lenient().when(request.getHeader("X-UserInfo")).thenReturn(userInfo);

        // When
        TenantContext.setContext(request);

        // Then
        assertThat(TenantContext.getUserName()).isEqualTo("extracted_user");
    }

    @Test
    void shouldGetAdapterHeaderMap() {
        // Given
        TenantContext.setContext(Map.of(
                "X-TenantId", "123",
                "X-OrganizationId", "456",
                "X-Auth-Request-Preferred-Username", "testuser"
        ));

        // When
        Map<String, String> headerMap = TenantContext.getAdapterHeaderMap();

        // Then
        assertThat(headerMap).containsEntry("X-TenantId", "123");
        assertThat(headerMap).containsEntry("X-OrganizationId", "456");
        assertThat(headerMap).containsEntry("X-Auth-Request-Preferred-Username", "testuser");
    }

    @Test
    void shouldSetTenantId() {
        // Given
        TenantContext.setContext(Map.of(
                "X-TenantId", "123",
                "X-OrganizationId", "456",
                "X-Auth-Request-Preferred-Username", "testuser"
        ));

        // When
        TenantContext.setTenantId("789");

        // Then
        assertThat(TenantContext.getTenantId()).isEqualTo("789");
        assertThat(TenantContext.getOrganizationId()).isEqualTo("456");
        assertThat(TenantContext.getUserName()).isEqualTo("testuser");
    }

    @Test
    void shouldRunWithTenantInfos() {
        // Given
        TenantContext.setContext(Map.of("X-TenantId", "123"));
        boolean[] executed = {false};

        // When
        TenantContext.withTenantInfos(() -> executed[0] = true);

        // Then
        assertThat(executed[0]).isTrue();
    }
}
