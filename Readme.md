# Karaf Validation

This project provides support for Bean Validation inside Karaf. Karaf provides enterprise feature set, however it's not 
granular enough, so it comes with everything at same time. More over it installs multiple versions of validation spec at
same time.

Requires
---
Karaf 3.x

Provides
---
Bean validation features for running it inside karaf.

## Developer introduction

To start using validation in your project you need to install karaf feature set:

```
feature:repo-add mvn:org.code-house.validation/features/3.0.0-SNAPSHOT/xml/features
feature:install bval-validation-provider
```