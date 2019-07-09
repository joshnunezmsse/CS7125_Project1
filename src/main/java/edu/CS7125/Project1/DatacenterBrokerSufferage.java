package edu.CS7125.Project1;

import java.util.ArrayList;
import org.cloudbus.cloudsim.DatacenterBroker;

class DatacenterBrokerSufferage extends DatacenterBroker {

    public DatacenterBrokerSufferage(String name) throws Exception {
        super(name);
    }

    @Override
    public void startEntity() {
        // setup all the cloudlet to VM bindings to support Sufferage scheduling
        double[][] executionTimes = new double[cloudletList.size()][vmList.size()];

        // build execution time matrix
        for (int i=0; i < cloudletList.size(); i++) {
            for (int j=0; j < vmList.size(); j++) {
                executionTimes[i][j] = cloudletList.get(i).getCloudletLength() / vmList.get(j).getMips();
            }
        }

        // loops through all cloudlets determining VMs to bind to

        ArrayList<Integer> scheduled = new ArrayList<>();
        
        while (scheduled.size() < cloudletList.size()) {
            double minExecutionTime = Double.MAX_VALUE;
            double minExecutionTime2 = Double.MAX_VALUE;
            double currentSufferage = 0.0;
            int currentCloudlet = 0;
            int currentVm = 0;

            // find cloudlet with max sufferage
            for (int i=0; i < cloudletList.size(); i++) {
                if (!scheduled.contains(i)) {
                    if (executionTimes[i][0] < executionTimes[i][1]) {
                        minExecutionTime = executionTimes[i][0];
                        minExecutionTime2 = executionTimes[i][1];
                        currentVm = 0;
                    } else {
                        minExecutionTime = executionTimes[i][1];
                        minExecutionTime2 = executionTimes[i][0];
                        currentVm = 1;
                    }
                    for (int j=0; j < vmList.size(); j++) {
                        if (minExecutionTime < executionTimes[i][j]) {
                            currentVm = j;
                            minExecutionTime2 = minExecutionTime;
                            minExecutionTime = executionTimes[i][j];
                        }
                    }

                    if (currentSufferage < (minExecutionTime2 - minExecutionTime)) {
                        currentSufferage = minExecutionTime2 - minExecutionTime;
                        currentCloudlet = i;
                    }
                }
            }

            // find vm with min execution time for the selected cloudlet
            minExecutionTime = Double.MAX_VALUE;
            for (int j=0; j < vmList.size(); j++) {
                if (minExecutionTime > executionTimes[currentCloudlet][j]) {
                    minExecutionTime = executionTimes[currentCloudlet][j];
                    currentVm = j;
                }
            }

            // set the vm for the cloudlet
            cloudletList.get(currentCloudlet).setVmId(vmList.get(currentVm).getId());
            scheduled.add(currentCloudlet);

            // update the completion times
            for (int i=0; i < cloudletList.size(); i++) {
                executionTimes[i][currentVm] += cloudletList.get(currentCloudlet).getCloudletLength() / vmList.get(currentVm).getMips();
            }
        }

        // continue starting entity
        super.startEntity();
    }
}