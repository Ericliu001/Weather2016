# Weather2016
A new weather app to demonstrate the patterns that can be used in Android development.

There are 2 productFlavors: full is normal flavor which retrieves real data from the server; mock is using mock data from a json file saved in the raw directory.




    productFlavors {
        mock {
            applicationIdSuffix = ".mock"
            versionName "1.0-mock"
        }
        full {
            applicationIdSuffix = ".full"
            versionName "1.0-full"
        }
    }





![Alt text](https://docs.google.com/uc?export=download&id=0BwmSBnU6HzgSTzdSQ2RpVG5Jalk "Optional title")



Libraries
---------

Gson - https://github.com/google/gson

OkHttp - http://square.github.io/okhttp

RxAndroid - https://github.com/ReactiveX/RxAndroid

Dagger2 - http://google.github.io/dagger/
