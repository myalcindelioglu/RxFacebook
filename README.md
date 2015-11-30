# RxFacebook

This library allows the usage of RxJava with the facebook SDK.

Example (With Retrolambda for brevity, but not required):

```java
RxFacebook.getInstance(accessToken)
                  .request(Fields.ID,
                           Fields.NAME,
                           Fields.BIRTHDAY,
                           Fields.USER_FRIENDS,
                           Fields.GENDER)
                  .subscribe(facebookUser -> {
                    // use your facebookUser POJO as you wish :)
                  });

 ```

Look at the `sample` app for more.

## Status

**This library is at a early stage of development, so contributions are welcome.**

## Benefits

- Avoid worrying about the facebook SDK;
- Forget about messy callbacks;
- Easy testing;
- All what RX provides about transformation, filter, chaining...
<!--
## Setup

In your build.gradle :

```gradle
dependencies {
    compile 'com.cesarferreira.rxfacebook:rxfacebook:0.1.0@aar'
}
```
-->
