pluginManagement {
    repositories {
        maven {
            setUrl("https://maven.aliyun.com/repository/public")  // 阿里云镜像
        }
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {
            setUrl("https://maven.aliyun.com/repository/public")  // 阿里云镜像
        }
        google()
        mavenCentral()
    }
}

rootProject.name = "AndroidCompose_Learn"
include(":app")
 