package com.cp2396f09_sem4_grp3.onmart_service.event;

import org.springframework.context.ApplicationEvent;

import com.cp2396f09_sem4_grp3.onmart_common.entities.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationCompleteEvent extends ApplicationEvent {
    private User user;
    private String applicationUrl;

    @Builder
    public RegistrationCompleteEvent(User user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}