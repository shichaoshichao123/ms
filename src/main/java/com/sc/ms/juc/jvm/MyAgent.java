package com.sc.ms.juc.jvm;

import com.sun.xml.internal.ws.org.objectweb.asm.*;
import jdk.internal.org.objectweb.asm.tree.*;

import java.lang.instrument.*;
import java.security.ProtectionDomain;


/**
 * @author: shichao
 * @date: 2019/6/4
 * @description:
 */
public class MyAgent {

    public static void premain(String args, Instrumentation instrumentation) {
        instrumentation.addTransformer(new MyTransformer());
    }

    static class MyTransformer implements ClassFileTransformer, Opcodes {

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            if (className.startsWith("java")    ||
                    className.startsWith("javax")   ||
                    className.startsWith("jdk")     ||
                    className.startsWith("sun")     ||
                    className.startsWith("com/sun") ||
                    className.startsWith("org/example")) {
                // Skip JDK classes and profiler classes
                return null;
            }

            ClassReader cr = new ClassReader(classfileBuffer);
            ClassNode classNode = new ClassNode(1);
            cr.accept((ClassVisitor) classNode, ClassReader.SKIP_FRAMES);

            for (MethodNode methodNode : classNode.methods) {
                for (AbstractInsnNode node : methodNode.instructions.toArray()) {
                    if (node.getOpcode() == NEW) {
                        TypeInsnNode typeInsnNode = (TypeInsnNode) node;

                        InsnList instrumentation = new InsnList();
                        instrumentation.add(new LdcInsnNode(Type.getObjectType(typeInsnNode.desc)));
                        instrumentation.add(new MethodInsnNode(INVOKESTATIC, "org/example/MyProfiler", "fireAllocationEvent",
                                "(Ljava/lang/Class;)V", false));

                        methodNode.instructions.insert(node, instrumentation);
                    }
                }
            }

            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            classNode.accept(cw);
            return cw.toByteArray();
        }
    }

}
