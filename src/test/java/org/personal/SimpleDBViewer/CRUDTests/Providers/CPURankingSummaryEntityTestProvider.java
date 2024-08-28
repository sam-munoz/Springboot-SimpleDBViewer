package org.personal.SimpleDBViewer.CRUDTests.Providers;

import org.junit.jupiter.params.provider.Arguments;
import org.personal.SimpleDBViewer.Domain.CPUListEntity;
import org.personal.SimpleDBViewer.Domain.CPURankingSummaryEntity;
import java.util.stream.Stream;

public class CPURankingSummaryEntityTestProvider {
    /**
     * Provider for method <code>createSummaryProvider</code>
     * @return Provides arguments for the method above
     */
    public static Stream<Arguments> testCreateSummaryProvider() {
        return Stream.of(
            Arguments.arguments(
                    new CPURankingSummaryEntity(
                        new CPUListEntity("i7-11700KF"),
                        100,
                        12L
                    ),
                    false
            ),
            Arguments.arguments(
                    new CPURankingSummaryEntity(
                        new CPUListEntity("i3-8100"),
                        12,
                        2L
                    ),
                    false
            ),
            Arguments.arguments(
                    new CPURankingSummaryEntity(
                        new CPUListEntity("Ryzen 5 5600X"),
                        null,
                        null
                    ),
                    false
            ),
            Arguments.arguments(
                    new CPURankingSummaryEntity(
                        null,
                        12,
                        30L
                    ),
                    false
            ),
            Arguments.arguments(
                    null,
                    false
            ),
            Arguments.arguments(
                    null,
                    true
            )
        );
    }

    /**
     * Provider for method <code>getSummaryProvider</code>
     * @return Provides arguments for the method above
     */
    public static Stream<CPURankingSummaryEntity> testGetSummaryProvider() {
        return Stream.of(
            new CPURankingSummaryEntity(new CPUListEntity("i7-11700KF"), null, null),
            new CPURankingSummaryEntity(3L, null, null, null),
            new CPURankingSummaryEntity(null, new CPUListEntity(5L), null, null),
            null
        );
    }

    /**
     * Provider for method <code>updateSummaryProvider</code>
     * @return Provides arguments for the method above
     */
    public static Stream<Arguments> testUpdateSummaryProvider() {
        return Stream.of(
            Arguments.arguments(
                new CPURankingSummaryEntity(1L, new CPUListEntity("Ryzen 5 5600X"), 1000, 200L),
                true
            ),
            Arguments.arguments(
                new CPURankingSummaryEntity(4L, new CPUListEntity(4L, "i7-11700KF"), 10, 2L),
                false
            )
        );
    }

    /**
     * Provider for method <code>deleteSummaryProvider</code>
     * @return Provides arguments for the method above
     */
    public static Stream<CPURankingSummaryEntity> testDeleteSummaryProvider() {
        return Stream.of(
                new CPURankingSummaryEntity(1L, new CPUListEntity(1L), null, null),
                new CPURankingSummaryEntity(4L, new CPUListEntity(4L), null, null),
                new CPURankingSummaryEntity(1L, new CPUListEntity(1L),  null, null),
                new CPURankingSummaryEntity(1L, new CPUListEntity(2L),  null, null),
                new CPURankingSummaryEntity(null,  null, null),
                null
        );
    }
}
