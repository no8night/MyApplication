# MyApplication

一个简单的库，添加了一个下拉刷新listview（swiperefreshlayout 和 listview的组合）控件

一个基于HTTPURLConnection的工具类，写了一些通用的get和post方法

To get a Git project into your build:
Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        compile 'com.github.no8night:MyApplication:v1.0.0'
	}
