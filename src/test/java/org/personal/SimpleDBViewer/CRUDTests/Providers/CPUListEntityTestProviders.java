package org.personal.SimpleDBViewer.CRUDTests.Providers;

import org.personal.SimpleDBViewer.Domain.CPUListEntity;

import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

public class CPUListEntityTestProviders {
    /**
     * Returns a Stream of CPUListEntity used to test the testGetCPU method
     * @return Returns the steam stated in the description
     */
    public static Stream<CPUListEntity> testGetCPUProvider() {
        CPUListEntity c0 = new CPUListEntity();
        c0.setId(1L);
        return Stream.of(
                new CPUListEntity("i7-11700KF"),
                new CPUListEntity("i3-8100"),
                new CPUListEntity(),
                null
        );
    }

    /**
     * Returns a Stream of CPUListEntity used to test the testCreateCPU method
     * @return Returns the steam stated in the description
     */
    public static Stream<CPUListEntity> testCreateCPUProvider() {
        return Stream.of(
                new CPUListEntity("i7-11700KF"),
                new CPUListEntity("i3-8100"),
                new CPUListEntity(),
                null
        );
    }

    /**
     * Returns a Stream of CPUListEntity used to test the testDeleteCPU method
     * @return Returns the steam stated in the description
     */
    public static Stream<CPUListEntity> testDeleteCPUProvider() {
        return Stream.of(
                new CPUListEntity("i7-11700KF"),
                new CPUListEntity("i3-8100"),
                new CPUListEntity("Ryzen 5 5600X"),
                new CPUListEntity(),
                null
        );
    }

    /**
     * Returns a Stream of CPUListEntity used to test the testUpdateCPU method
     * @return Returns the steam stated in the description
     */
    public static Stream<Arguments> testUpdateCPUProvider() {
        return Stream.of(
                Arguments.arguments(new CPUListEntity("i7-11700KF"), "i3-8100"),
                Arguments.arguments(new CPUListEntity("i3-8100"), "i7-11700KF"),
                Arguments.arguments(new CPUListEntity("i3-8100"), ""),
                Arguments.arguments(new CPUListEntity("Ryzen 5 5600X"), "i3-8100"),
                Arguments.arguments(new CPUListEntity(), "i3-8100"),
                Arguments.arguments(null, "i3-8100")
        );
    }
}
