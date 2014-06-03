/*
 * Copyright (C) 2012 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.exoplatform.addons.persistence.bootstrap;

import org.exoplatform.addons.persistence.listener.GuiceManager;
import org.exoplatform.addons.persistence.services.*;

public class ServiceBootstrap {

    private static UserService userService;
    private static TokenService tokenService;
    private static ChatService chatService;
    private static NotificationService notificationService;
    private static StatisticsService statisticsService;

    public static void forceNew() {

        chatService = GuiceManager.getInstance().getInstance(ChatService.class);
        userService = GuiceManager.getInstance().getInstance(UserService.class);
        tokenService = GuiceManager.getInstance().getInstance(TokenService.class);
        notificationService = GuiceManager.getInstance().getInstance(NotificationService.class);
        statisticsService = GuiceManager.getInstance().getInstance(StatisticsService.class);
    }

    public static UserService getUserService() {
        return userService;
    }

    public static TokenService getTokenService() {
        return tokenService;
    }

    public static ChatService getChatService() {

        return chatService;
    }

    public static NotificationService getNotificationService() {
        return notificationService;
    }

    public static StatisticsService getStatisticsService() {
        return statisticsService;
    }
}
