# CS7125_Project1
# Results
## First come first served
```
========== OUTPUT ==========
    Cloudlet ID     STATUS       Data center ID      VM ID            Time      Start Time      Finish Time        Submission Time
              4    SUCCESS          Datacenter0          1             100             0.1           100.1                     0.1
              5    SUCCESS          Datacenter0          2           112.5             0.1           112.6                     0.1
              6    SUCCESS          Datacenter0          0          133.33             0.1          133.43                     0.1
              7    SUCCESS          Datacenter0          1          133.44             0.1          133.54                     0.1
              2    SUCCESS          Datacenter0          2           142.5             0.1           142.6                     0.1
              1    SUCCESS          Datacenter0          1          153.38             0.1          153.48                     0.1
              8    SUCCESS          Datacenter0          2             190             0.1           190.1                     0.1
              0    SUCCESS          Datacenter0          0          213.33             0.1          213.43                     0.1
              3    SUCCESS          Datacenter0          0          306.66             0.1          306.76                     0.1
              9    SUCCESS          Datacenter0          0          366.66             0.1          366.76                     0.1

Throughput: 366.66, Turnaround: 185.18
```
----
## Shortest first
```
========== OUTPUT ==========
    Cloudlet ID     STATUS       Data center ID      VM ID            Time      Start Time      Finish Time        Submission Time
              5    SUCCESS          Datacenter0          1              75             0.1            75.1                     0.1
              2    SUCCESS          Datacenter0          1              95             0.1            95.1                     0.1
              8    SUCCESS          Datacenter0          1          126.67             0.1          126.77                     0.1
              6    SUCCESS          Datacenter0          0          133.33             0.1          133.43                     0.1
              0    SUCCESS          Datacenter0          2             135             0.1           135.1                     0.1
              7    SUCCESS          Datacenter0          2             195             0.1          195.09                     0.1
              1    SUCCESS          Datacenter0          2             225             0.1          225.09                     0.1
              4    SUCCESS          Datacenter0          0          233.33             0.1          233.43                     0.1
              3    SUCCESS          Datacenter0          0          313.32             0.1          313.43                     0.1
              9    SUCCESS          Datacenter0          0          373.32             0.1          373.43                     0.1

Throughput: 373.33, Turnaround: 190.50
```
----
## Min Min
```
========== OUTPUT ==========
    Cloudlet ID     STATUS       Data center ID      VM ID            Time      Start Time      Finish Time        Submission Time
              6    SUCCESS          Datacenter0          1           83.33             0.1           83.43                     0.1
              5    SUCCESS          Datacenter0          2          112.49             0.1          112.59                     0.1
              4    SUCCESS          Datacenter0          0          133.33             0.1          133.43                     0.1
              0    SUCCESS          Datacenter0          1          136.67             0.1          136.77                     0.1
              2    SUCCESS          Datacenter0          1          151.66             0.1          151.76                     0.1
              7    SUCCESS          Datacenter0          2          187.49             0.1          187.59                     0.1
              3    SUCCESS          Datacenter0          1          188.33             0.1          188.43                     0.1
              8    SUCCESS          Datacenter0          0             200             0.1           200.1                     0.1
              1    SUCCESS          Datacenter0          1             205             0.1           205.1                     0.1
              9    SUCCESS          Datacenter0          2           237.5             0.1           237.6                     0.1

Throughput: 237.50, Turnaround: 163.58
```
----
## Max Min
```
========== OUTPUT ==========
    Cloudlet ID     STATUS       Data center ID      VM ID            Time      Start Time      Finish Time        Submission Time
              6    SUCCESS          Datacenter0          1           83.33             0.1           83.43                     0.1
              5    SUCCESS          Datacenter0          2          112.49             0.1          112.59                     0.1
              4    SUCCESS          Datacenter0          0          133.33             0.1          133.43                     0.1
              0    SUCCESS          Datacenter0          1          136.67             0.1          136.77                     0.1
              2    SUCCESS          Datacenter0          1          151.67             0.1          151.77                     0.1
              7    SUCCESS          Datacenter0          2           187.5             0.1          187.59                     0.1
              3    SUCCESS          Datacenter0          1          188.33             0.1          188.43                     0.1
              8    SUCCESS          Datacenter0          0             200             0.1           200.1                     0.1
              1    SUCCESS          Datacenter0          2           217.5             0.1           217.6                     0.1
              9    SUCCESS          Datacenter0          1          218.33             0.1          218.43                     0.1

Throughput: 218.33, Turnaround: 162.91
```
----
## Suffrage
```
========== OUTPUT ==========
    Cloudlet ID     STATUS       Data center ID      VM ID            Time      Start Time      Finish Time        Submission Time
              6    SUCCESS          Datacenter0          0             100             0.1           100.1                     0.1
              5    SUCCESS          Datacenter0          2          112.49             0.1          112.59                     0.1
              0    SUCCESS          Datacenter0          1          119.99             0.1          120.09                     0.1
              4    SUCCESS          Datacenter0          1          129.99             0.1          130.09                     0.1
              2    SUCCESS          Datacenter0          2          142.49             0.1          142.59                     0.1
              7    SUCCESS          Datacenter0          1          163.33             0.1          163.43                     0.1
              1    SUCCESS          Datacenter0          1          183.33             0.1          183.43                     0.1
              8    SUCCESS          Datacenter0          2             190             0.1          190.09                     0.1
              3    SUCCESS          Datacenter0          0          246.66             0.1          246.76                     0.1
              9    SUCCESS          Datacenter0          0          306.66             0.1          306.76                     0.1

Throughput: 306.66, Turnaround: 169.49
```
----
# Comparison
```
Throughput | 366.66 |  373.33 | 237.50 | 218.33 | 306.66
Turnaround | 185.18 |  190.50 | 163.58 | 162.91 | 169.49
-----------------------------------------------------------
              FCFS  |  Short  | MinMin | MaxMin | Suffrage
```