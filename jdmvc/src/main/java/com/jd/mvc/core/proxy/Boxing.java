package com.jd.mvc.core.proxy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class Boxing {
	static final Map<String, Boxing> BOXERS;
	static {
		Map<String, Boxing> map = new HashMap<String, Boxing>();
		addEntry(map, BytecodePrimitive.BOOLEAN);
		addEntry(map, BytecodePrimitive.BYTE);
		addEntry(map, BytecodePrimitive.CHAR);
		addEntry(map, BytecodePrimitive.DOUBLE);
		addEntry(map, BytecodePrimitive.FLOAT);
		addEntry(map, BytecodePrimitive.INT);
		addEntry(map, BytecodePrimitive.LONG);
		addEntry(map, BytecodePrimitive.SHORT);
		BOXERS = Collections.unmodifiableMap(map);
	}

	static void addEntry(Map<String, Boxing> map, BytecodePrimitive primitive) {
		String jvmClassName = ClassFileWriterContext.jvmClassName(primitive.getWrapperClassName());
		Boxing boxing = new Boxing(jvmClassName, primitive.getValueMethodName(), "()" + primitive.getArrayComponentName(), "("
		        + primitive.getArrayComponentName() + ")L" + jvmClassName + ";");
		map.put(primitive.getName(), boxing);
		map.put(primitive.getArrayComponentName(), boxing);
	}

	private final String className;
	private final String unboxMethodName;
	private final String unboxMethodDescriptor;
	private final String boxMethodDescriptor;

	static Boxing getUnboxer(Class<?> clazz) {
		return BOXERS.get(clazz.getName());
	}

	Boxing(String className, String methodName, String unboxMethodDescriptor, String boxMethodDescriptor) {
		this.className = className;
		this.unboxMethodName = methodName;
		this.unboxMethodDescriptor = unboxMethodDescriptor;
		this.boxMethodDescriptor = boxMethodDescriptor;
	}

	String getClassName() {
		return className;
	}

	String getUnboxMethodName() {
		return unboxMethodName;
	}

	String getUnboxMethodDescriptor() {
		return unboxMethodDescriptor;
	}

	String getBoxMethodName() {
		return "valueOf";
	}

	String getBoxMethodDescriptor() {
		return boxMethodDescriptor;
	}
}