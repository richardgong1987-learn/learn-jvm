# 内存结构

## 1. 程序计数器

## 2. 虚拟机栈

## 3. 本地方法栈

## 4. 堆

## 5. 方法区



## 直接内存 Direct Memory

操作系统内存

- 常见于NIO操作时,用于数据缓冲区

  - 性能调优:

    使用nio的ByteBuffer,直接开启直接内存. 提高读写效率

    ```java
    ByteBuffer.allocateDirect(1024);
    ```

  因为少了java缓冲区.

  直接利用系统内存.少了复制过程,所以就性能高了.

- 分配回收成本较高,写读写性能很高

- 不受JVM内存回收管理.但借助监控虚引用(Cleaner)来触发原生方法释放内存

  注意:

  - 内存溢出

  - 查看直接内存时,不能通过jvm工具来看,因为它不属于jvm管理,要用操作系统工具

  - 手动释放直接内存的方式是通过Unsafe对象的freeMemory()来操作

  - 禁用显式垃圾回收的原因是:显示垃圾回收会回收新生代,老年代的回收,比较占用资源

  - 如果在禁用显式垃圾回收的情况时,可以用freeMomery()手动释放内存来管理内存

    

  



## 方法区

**定义:**所有java虚拟机的线程的共享区域

**保存着**:类结构,方法,构造函数.字段,运行时常量池

**创建:**方法区在虚拟机启动时被创建,逻辑上是堆的组成部分



### 运行时常量池constant pool:放到StringTable表里

可以看成是一张sql常量表

然后,会有很多#id



源码:

```
package com.example.learningjvm;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("hello world");
    }
}
```

反编译:

```
Warning: Binary file HelloWorld contains com.example.learningjvm.HelloWorld
Classfile /Users/jack.g/Documents/learning-jvm/target/classes/com/example/learningjvm/HelloWorld.class
  Last modified 03 31, 20; size 611 bytes
  MD5 checksum 3516f84e7b8b7f73b8342488a6608700
  Compiled from "HelloWorld.java"
public class com.example.learningjvm.HelloWorld
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Methodref          #6.#21         // java/lang/Object."<init>":()V
   #2 = Fieldref           #22.#23        // java/lang/System.out:Ljava/io/PrintStream;
   #3 = String             #24            // hello world
   #4 = Methodref          #25.#26        // java/io/PrintStream.println:(Ljava/lang/String;)V
   #5 = Class              #27            // com/example/learningjvm/HelloWorld
   #6 = Class              #28            // java/lang/Object
   #7 = Utf8               <init>
   #8 = Utf8               ()V
   #9 = Utf8               Code
  #10 = Utf8               LineNumberTable
  #11 = Utf8               LocalVariableTable
  #12 = Utf8               this
  #13 = Utf8               Lcom/example/learningjvm/HelloWorld;
  #14 = Utf8               main
  #15 = Utf8               ([Ljava/lang/String;)V
  #16 = Utf8               args
  #17 = Utf8               [Ljava/lang/String;
  #18 = Utf8               MethodParameters
  #19 = Utf8               SourceFile
  #20 = Utf8               HelloWorld.java
  #21 = NameAndType        #7:#8          // "<init>":()V
  #22 = Class              #29            // java/lang/System
  #23 = NameAndType        #30:#31        // out:Ljava/io/PrintStream;
  #24 = Utf8               hello world
  #25 = Class              #32            // java/io/PrintStream
  #26 = NameAndType        #33:#34        // println:(Ljava/lang/String;)V
  #27 = Utf8               com/example/learningjvm/HelloWorld
  #28 = Utf8               java/lang/Object
  #29 = Utf8               java/lang/System
  #30 = Utf8               out
  #31 = Utf8               Ljava/io/PrintStream;
  #32 = Utf8               java/io/PrintStream
  #33 = Utf8               println
  #34 = Utf8               (Ljava/lang/String;)V
{
  public com.example.learningjvm.HelloWorld();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 3: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Lcom/example/learningjvm/HelloWorld;

  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=1, args_size=1
         0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
         3: ldc           #3                  // String hello world
         5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
         8: return
      LineNumberTable:
        line 5: 0
        line 6: 8
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       9     0  args   [Ljava/lang/String;
    MethodParameters:
      Name                           Flags
      args
}
SourceFile: "HelloWorld.java" 
```



注意:

jdk1.6版本中.串池都是保存在永久代. Perm space中

Jdk1.8版本后,串池都是保存在heap space中.

### stringTable垃圾回收

### stringTable性能调优

- 如何系统里常量数量非常多的情况

  可以调整StringTableSize的bucket个数,可以让性能更好

-XX:StringTableSize=20000

可以加大bucket大小, 从而性能会好一点.

- 如果有大量字符串并且有重复的

  可以使用.intern()方法,让它放到串池中.解决重复问题,节约内存

## 堆

- 定义:通过new关键字,创建对象都会使用堆内存

- 特点:

  - 它是线程共享的,堆中的对象都需要考虑线程安全问题
  - 有垃圾回收机制

  

### 堆内存溢出:

如:在list对象里,不断的add数据.



### 堆内存诊断:

#### 工具:

1. #### jps工具

   查询java所有进程

2. jmap工具

   查看堆内存占用情况

   **注意:**

   eden space是用来保存new 出来的堆内存

3. Jconsole 工具

   多功能监测,可以连接监测

4. JvisualVM

   



## 本地方法栈

## 程序计数器

作用:记住下一条jvm指令的执行地址

### 特点

1. 线程私有,每个线程都有自己的程序计数器
2. 没有内存溢出

## 虚拟机栈

线程运行时所需要的内存空间

栈帧:每个方法运行时所需要的内存

(想像一下debug时是调用堆栈),每个方法,就是一个帧

活动栈帧:当前正在执行的方法



### 注意:

- `栈内存,不存在垃圾回收这个概念`答:.因为栈帧执行结束后,就出栈.

- 栈内存溢出

  1. 过多的递归

  2. 栈内存过大.超过主机内存

  3. 循环调用,如json相互转化,无限循环调用.  解决办法:切断无限循环

     

- `栈内存不是分配越大越好`答:.因为栈内存等于线程内存的大小.栈内存越大,线程数就越少. 比如:计算机的内存500M, 栈内存设置成1M,那么,线程数最多就是500个. 如果设置成2m栈内存,那么线程数最多只有250条线程

- `方法内部局部变量是否线程安全`   答:安全. 因为线程运行调用时,会产生一个栈帧. 每个线程会产生自己的局部变量,相互不干扰,没有逃离作用域范围.除非是共享变量才会有线程安全问题

  如:传入一个引用对象参数,或者,共享变量.都会有线程安全问题

  ### 

### 线程运行诊断:

案例1:cpu占用过高

定位:

- 用top定位那个进程cpu占用过高

- 用ps H -eo pid,tid,%cpu | grep 进程id(用ps命令进一步定位那一个线程cpu过高)

- Jstack 进程id   列出所有线程,注意看nid字段就是线程id,  注意把10进制换算成16进制.然后查看.

  可以根据线程id找到有问题的线程,进一步定位到问题代码的源码行号

  

案例2:程序运行很长时间都没有结果(死锁)

定位:

Jstack 可以发现Found one Jave-level deadlock:

可以在下面的

看到死锁的行号















