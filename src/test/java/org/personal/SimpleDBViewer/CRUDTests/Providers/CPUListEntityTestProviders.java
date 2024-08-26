package org.personal.SimpleDBViewer.CRUDTests.Providers;

import org.personal.SimpleDBViewer.Domain.CPUListEntity;

import java.util.stream.Stream;

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
}
