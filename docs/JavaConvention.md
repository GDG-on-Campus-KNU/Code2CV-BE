# JavaConvention

아래 정의되어 있는 가이드외에는 [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)를 따른다.

## Naming Rules
### Package
- package 이름은 소문자로 작성한다.
```kotlin
package kr.co.prnd.domain
```
- underscore(`_`), camelCase는 사용하지 않는다.
```kotlin
// WRONG!
package com.example.my_project
package com.example.myProject
```

### 함수 이름
함수 이름은 camelCase로 작성한다.

- `@NonNull`을 반환하는 함수는 이름에 `get`을 붙인다.
```
getXXX()
```

- `Optional`을 반환하는 함수는 이름에 `find`을 붙인다.
```
findXXX()
```

- 복수형을 가져올때는 뒤에 s를 붙인다
```
getBrands()      // O
getBrandList()   // X
```


## Line
- 1줄에 100자를 넘지 않도록 작성한다.

### Parameter
- Parameter개수가 많아서 줄바꿈이 필요한 경우, **`,`다음부터 줄바꿈한다.**
```java
public InputSingleTextView(Context context,  
  @RegisterStep String step,  
  String hint,  
  String validationMessage,  
  @NonNull CompleteListener completeListener) {  
  ...
}
```
- 줄바꿈이 필요한 부분부터 줄바꿈 하지 않고 위의 예시처럼 1개 단위로 줄바꿈을 해준다.
- 이런 함수를 호출하는 코드에서도 같은 패턴으로 맞춰서 호출한다.
```java
new InputSingleTextView(this,
   RegisterStep.XXX,
   getString(R.string.xxx),
   getString(R.string.xxx),
   completeListener);
```


### Operator
- 많은 operator의 연산으로 줄바꿈이 필요한 경우, **operator 전에 줄바꿈한다.**
```java
int longName = anotherVeryLongVariable + anEvenLongerOne - thisRidiculousLongOne
        + theFinalOne;
```
- 연산자의 경우는 줄바꿈이 필요한 위치부터 줄바꿈한다.

### Method chain
- Builder/RxJava 등 여러 함수를 chaining으로 사용하면서 줄바꿈이 필요한 경우, **`.`전에 줄바꿈한다.**
```java
ImageLoader.load(user.getProfileUrl())  
        .placeholder(R.drawable.img_user_placeholder)  
        .fitCenter()  
        .into(binding.ivUser);
```

## 새파일 생성시 주석
- 새로운 파일을 만들때 자동으로 만들어지는 주석은 만들지 않는다.
- 예)
```java
/**
 * Created by ted on 2018-07-12.
 */
```


## Util/Helper/Manager
- 특정기능을 수행하거나 상태를 관리하거나 분리되어 동작을 수행하는 클래스에 대한 사용처별 이름을 정의한다.
### Util
- `public static void AAA`등으로 쓰이는 여러곳에서 사용되는 util성 기능을 보아둔 클래스
- `aa.bb.cc.util` 패키지에 모두 모아둔다.
- 예) `DateFormatUtil`, `PixelUtil`, `BitmapUtil`등
### Helper
- 특정 패키지나 기능에서 한정되어 사용되는 `public static void AAA` 클래스
- 공통으로 쓰이지 않고 특정 기능의 코드를 분리하기 위한 용도로 사용한다.
- 예) `NotificationChannelHelper`등
### Manager
- 항상 내부에서 instance로 만들어서 관리되는 용도
- 내부적으로 state 혹은 information을 가지고 있어서 호출한곳에서의 상태에 따라서 관리되는 값을 변경하고 반영하는 작업을 해준다.
- 예) `RegisterStepManager`, `RegisterCarInfoConfirmManager`등

## if문
- `if (isChecked == false)` 와 같은 코드는 명백한 Lint warning이므로 사용하지 않는다.
```java
if (isUnchekced)
```

- 조건문에 `!`를 넣는것대신 아래와 같은 규칙으로 코드를 작성한다.

1. 조건문에서 체크되는 boolean 변수/함수는 항상 긍정문으로 작성한다.
   `if(isChecked())`
2. 이미 부정문으로 작성되어 있는 함수가 있다면 이를 재사용하여 함수를 작성한다.
   `if(isUnchekced())`

```
private boolean isUnchekced(){
        return !isChecked();
}
```

## 기타
- `import static xx.xx.xx;`는 `테스트코드`를 제외하고 사용하지 않는다. ([Avoid static imports](https://carlosbecker.com/posts/avoid-static-imports/))
- `try/catch`를 사용한경우, Exception에 대한 처리를 항상 넣어준다.
```java
} catch (Exception e) {
    AnalyticsUtil.logException(e);
}
```