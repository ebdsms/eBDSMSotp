#include <jni.h>
#include <string>


extern "C" JNIEXPORT jstring
JNICALL
Java_com_ebdsms_otp_eBDSMS_getKey(JNIEnv *env, jobject object) {
    std::string encrypted_key = "dGF5ZWI0M253Y29kZXJCRDIwMjQ=";
    return env->NewStringUTF(encrypted_key.c_str());
}

extern "C" JNIEXPORT jstring
JNICALL
Java_com_tayebmahmud_otp_OtpActivity_getKey2(JNIEnv *env, jobject object) {
    std::string encrypted_key = "dGF5ZWI0M253Y29kZXJCRDIwMjQ=";
    return env->NewStringUTF(encrypted_key.c_str());
}

extern "C" JNIEXPORT jstring
JNICALL
Java_com_ebdsms_otp_eBDSMS_getBAS1(JNIEnv *env, jobject object) {
    std::string encrypted_key = "aHR0cHM6Ly9lYmRzbXMuY29tL1N0b3JlT1RQL2FwaS5waHA/YWN0aW9uPXBvc3Qtb3Rw";
    return env->NewStringUTF(encrypted_key.c_str());
}

extern "C" JNIEXPORT jstring
JNICALL
Java_com_tayebmahmud_otp_OtpActivity_getBAS2(JNIEnv *env, jobject object) {
    std::string encrypted_key = "aHR0cHM6Ly9lYmRzbXMuY29tL1N0b3JlT1RQL2FwaS5waHA/YWN0aW9uPXF1ZXJ5LW90cA==";
    return env->NewStringUTF(encrypted_key.c_str());
}