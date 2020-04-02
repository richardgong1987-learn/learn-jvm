# 类加载和字节码技术

## 类文件结构

尝试:

```
cafe babe 0000 0034 001f 0a00 0600 1109
0012 0013 0800 140a 0015 0016 0700 1707
0018 0100 063c 696e 6974 3e01 0003 2829
5601 0004 436f 6465 0100 0f4c 696e 654e
756d 6265 7254 6162 6c65 0100 046d 6169
6e01 0016 285b 4c6a 6176 612f 6c61 6e67
2f53 7472 696e 673b 2956 0100 104d 6574
686f 6450 6172 616d 6574 6572 7301 0004
6172 6773 0100 0a53 6f75 7263 6546 696c
6501 000f 4865 6c6c 6f57 6f72 6c64 2e6a
6176 610c 0007 0008 0700 190c 001a 001b
0100 0b68 656c 6c6f 2077 6f72 6c64 0700
1c0c 001d 001e 0100 2263 6f6d 2f65 7861
6d70 6c65 2f6c 6561 726e 696e 676a 766d
2f48 656c 6c6f 576f 726c 6401 0010 6a61
7661 2f6c 616e 672f 4f62 6a65 6374 0100
106a 6176 612f 6c61 6e67 2f53 7973 7465
6d01 0003 6f75 7401 0015 4c6a 6176 612f
696f 2f50 7269 6e74 5374 7265 616d 3b01
0013 6a61 7661 2f69 6f2f 5072 696e 7453
7472 6561 6d01 0007 7072 696e 746c 6e01
0015 284c 6a61 7661 2f6c 616e 672f 5374
7269 6e67 3b29 5600 2100 0500 0600 0000
0000 0200 0100 0700 0800 0100 0900 0000
1d00 0100 0100 0000 052a b700 01b1 0000
0001 000a 0000 0006 0001 0000 0003 0009
000b 000c 0002 0009 0000 0025 0002 0001
0000 0009 b200 0212 03b6 0004 b100 0000
0100 0a00 0000 0a00 0200 0000 0500 0800
0600 0d00 0000 0501 000e 0000 0001 000f
0000 0002 0010 
```

分析字节码:

### 1.1魔数信息

```
0-3字节:
```

cafe babe  表示它是否是class类型的文件 .class类型的文件都是cafebabe开头的

### 1.2版本

```
4-7字节,表示类的版本 0000 00034表示52,52就是jdk8
```

0000 0034  这里是16进制,就是52,就代码jdk8

```
8-9字节,表示常量池长度,表示常量池的有#1~#34项. 注意:0#项不计入,也没有值
```

 001f = 31

于是就有后面的31个字节码都是表示常量信息

 

### 1.3访问标记与继承信息







## 2.字节码指令

### 2.1 入门





## 3.透过字节码认识java语句本质

#### 3.1 if else语句本质上也是C语言的goto语句



#### 3.2 循环条件语句本质上是C语言的goto语句

#### 3.3 for循环本质也是goto语句

#### 3.4 do while不是goto语句,而是if_icmplt判断,并且,会跳转的语句

#### 3.5 静态代码块,本质都是从上到下顺序.无论有多个块静态代码块,都会合并 成一个方法去执行

#### 3.6 非静态代码块,也会像静态代码块一样被收集起来.放了构造函数里的上面. 先执行.

如:

```
package com.example.learningjvm;

public class Block {
    private String a = "s1";

    {
        b = 20;
    }

    private int b = 10;

    {
        a = "s2";
    }

    public Block() {

    }

    public Block(String a, int b) {
        this.a = a;
        this.b = b;
    }

    public static void main(String[] args) {
        Block d = new Block("s3", 30);
        System.out.println(d.a);
        System.out.println(d.b);
    }
}
```

会被重写 成:



```java
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.learningjvm;

public class Block {
    private String a = "s1";
    private int b = 20;

    public Block() {
        this.b = 10;
        this.a = "s2";
    }

    public Block(String a, int b) {
        this.b = 10;
        this.a = "s2";
        this.a = a;
        this.b = b;
    }

    public static void main(String[] args) {
        Block d = new Block("s3", 30);
        System.out.println(d.a);
        System.out.println(d.b);
    }
}
```



## 2.10 多态的原理

当执行invokevirtual指令时,



1. 先通过栈帧的对象引用找到对象
2. 分析对象头,找到对象的实现class
3. Class结构中有vtable,它在加载的连接阶段就已经根据方便的重写规则生成好了
4. 查表得到方法的具体地址
5. 执行方法的字节码



## 2.11 异常处理

java中的

Try catch是通过 goto加Exception table结合实现的



在jdk7以后,出现multi-catch的情况,可以通过"|"来连接多个异常,合并多个异常的语法糖

```
try {
    int a = 0;
} catch (RuntimeException  |  ExceptionInInitializerError e){
    
}
```

本质上跟普通的try catch的字节码是一样的

#### finally的本质

finally的本质是finally的代码块都同时复制到try,catch的代码块中.并且,还会添加剩余的异常流程中

然后,分别复制finally代码到其中去.以实现代码,实际上就是3份.





### 2.13 synchronized的过程

synchronized会用monitorenter加锁

并用异常表.监控.监控lock对象引用. 不管是正常还是

不正常,都会用monitorexit解锁





## 编译期处理

语法糖

### 默认构造器

### 自动拆装箱

### 泛型集合取值

- 泛型察除

- 泛型信息被保留的

  - 只能获取方法参数泛型信息

  - 获取返回泛型参数信息



### 可变参数

##### 本质是数组



### foreach循环

- 本质是编译器生成i++这样的for循环

- 如果是循环list的,本质是会转换成迭代器形式的while循环

  但这种都要现实Iterable接口的





### Switch字符串语法糖本质

#### 而对于switch和字符串

本质并没有改变switch只支持char和int的

而是被重写成int

```
public class switchdemo {
    public static void main(String[] args) {
        String a = "";
        switch (a){
            case "aaaaa":
                System.out.println(a);
                break;
            case "hello":
                System.out.println(a);
                break;
        }
    }
}
```

反编译:

```
public class switchdemo {
    public switchdemo() {
    }

    public static void main(String[] args) {
        String a = "";
        byte var3 = -1;
        switch(a.hashCode()) {
        case 92567585:
            if (a.equals("aaaaa")) {
                var3 = 0;
            }
            break;
        case 99162322:
            if (a.equals("hello")) {
                var3 = 1;
            }
        }

        switch(var3) {
        case 0:
            System.out.println(a);
            break;
        case 1:
            System.out.println(a);
        }

    }
}
```

#### 而对于switch和枚举

本质是把枚举转成数组,然后,以数组下标为case



## 枚举类本质

本质上是class,并继承Enum

编译器重写枚举类

然后,编译成多个静态成员变量

本质就像是一个hashmap的key就是枚举. value就是枚举的实例对象





## Try-with-resource语法糖

本质是编译器会加一个finally代码块



## 方法重写时的桥接方法



## 匿名内存类

本质是编译器生成内部类

但是如果内部类引用外部方法参数又怎么生成 呢?

其实就是生成构造函数时,传入参数,并,添加属性

注意,这也就是为什么匿名内部引用局部变量时,必需是final的原因







## 类加载



## 静态方法





## 类加载器

### 三大类加载器

- Bootstrap ClassLoader
- Extension ClassLoader
- Application ClassLoader
- 自动义加载器



### 上级委派模式加载



## 线程上下文类加载器

Thread.currentContext.getContextClassLoader()



## 自定义类加载器



## 运行时优化

0层

1层

2层

3层

4层

## 方法内联优化

- 复制方法内的代码,粘贴到调用者的位置
- 进行常量折叠



## 字段优化



## 反射优化















