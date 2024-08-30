package org.personal.SimpleDBViewer.CRUDTests.Providers;

import org.personal.SimpleDBViewer.Domain.UsersEntity;

import java.util.stream.Stream;

public class UsersEntityTestsProvider {
    public static Stream<UsersEntity> testCreateUserProvider() {
        return Stream.of(
            new UsersEntity("User 1")
        );
    }

    public static Stream<UsersEntity> testGetUserProvider() {
        UsersEntity notIdentifiable = new UsersEntity();
        notIdentifiable.setPasswd("some password");
        return Stream.of(
                new UsersEntity("User 1"),
                new UsersEntity("Sam"),
                notIdentifiable,
                null
        );
    }

    public static Stream<UsersEntity> testUpdateUserProvider() {
        return Stream.of(
                new UsersEntity("New Name")
        );
    }

    public static Stream<UsersEntity> testDeleteUserProvider() {
        return Stream.of(
                new UsersEntity("Sam")
        );
    }
}
