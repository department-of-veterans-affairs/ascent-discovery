@Library('ascent@maven-deps') _

microservicePipeline {
    imageName = 'ascent/ascent-discovery'

    //Specify string of comma separated upstream projects that will
    //trigger this build if successful
    upstreamProjects = '../ascent-platform/development'

    /*********  CI Pipeline Testing Configuration ***********/
    /*
    Define a mapping of environment variables that will be populated with Vault token values
    from the associated vault token role
    */
    vaultTokens = [
        "VAULT_TOKEN": "ascent-platform"
    ]
    testEnvironment = ['docker-compose.yml', 'docker-compose.override.yml']
    serviceToTest = 'ascent-discovery'
    deployWaitTime = 60
    testVaultTokenRole = "ascent-platform"
    containerPort = 8761

    /*********  Deployment Configuration ***********/
    stackName = "discovery"
    serviceName = "ascent-discovery"

    //Default Deployment Configuration Values
    //composeFiles = ["docker-compose.yml"]
}