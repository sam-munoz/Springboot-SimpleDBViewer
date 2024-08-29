package org.personal.SimpleDBViewer.CRUDTests.Providers;

import org.junit.jupiter.params.provider.Arguments;
import org.personal.SimpleDBViewer.Domain.CPUListEntity;
import org.personal.SimpleDBViewer.Domain.UsersCPURankingEntity;
import org.personal.SimpleDBViewer.Domain.UsersEntity;

import java.util.stream.Stream;

public class UsersCPURankingEntityTestsProvider {
    public static Stream<UsersCPURankingEntity> testCreateRankingProvider() {
        return Stream.of(
            new UsersCPURankingEntity(
                new CPUListEntity("i3-8100"),
                new UsersEntity("The Special One"),
                10
            )
        );
    }

    public static Stream<UsersCPURankingEntity> testGetRankingProvider() {
        return Stream.of(
                new UsersCPURankingEntity(
                        new CPUListEntity("i7-11700KF"),
                        new UsersEntity(1L, "User 1"),
                        7
                )
        );
    }
    public static Stream<UsersCPURankingEntity> testDeleteRankingProvider() {
        return Stream.of(
                new UsersCPURankingEntity(
                        new CPUListEntity("i7-11700KF"),
                        new UsersEntity(1L, "User 1"),
                        7
                )
        );
    }
}
