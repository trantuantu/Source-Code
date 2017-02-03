#include <jni.h>
JNIEXPORT jstring JNICALL
Java_com_example_tuantu_jnicarogame_MainActivity_getMsgFromJni(JNIEnv
*env,
jobject instance
)
{

// TODO
return (*env)->NewStringUTF(env, "jni");
}

JNIEXPORT jstring JNICALL
Java_com_example_tuantu_jnicarogame_MainActivity_HelloWorld(JNIEnv *env, jobject instance,
                                                            jstring a_) {
    const char *a = (*env)->GetStringUTFChars(env, a_, 0);

    // TODO

    (*env)->ReleaseStringUTFChars(env, a_, a);

    return (*env)->NewStringUTF(env, a);
}




JNIEXPORT jint JNICALL
Java_com_example_tuantu_jnicarogame_MainActivity_checkWin__IILint_3_093_3_093_2I(JNIEnv *env,
                                                                                 jobject instance,
                                                                                 jint x, jint y,
                                                                                 jobjectArray arr,
                                                                                 jint n) {

}

JNIEXPORT jint JNICALL
Java_com_example_tuantu_jnicarogame_MainActivity_test(JNIEnv *env, jobject instance,
                                                      jobjectArray arr, jint x, jint y, jint n) {
    // TODO

    jint* localArray = (*env)->NewIntArray(env, 1000000);
    jint* newArr = (*env)->NewIntArray(env, 1000000);
    int a =0;
    int i = 0;

    //if (localArray == NULL) return;
    while (i < n){
        jintArray oneDim = (jintArray)(*env)->GetObjectArrayElement(env, arr, i);
        jint *element = (*env)->GetIntArrayElements(env, oneDim, 0);
        (*env)->SetIntArrayRegion(env, localArray, i * n, i * n + (n - 1), element);
        i++;
    }

    newArr = (*env)->GetIntArrayElements(env, localArray, 0);

    int countRow1 = 0;
    int countRow2 = 0;
    int space = 0;
    i = 0;
    while (i < n) {
        if (newArr[y * n + i] == 1)
        {
            countRow2 = 0;
            countRow1++;
            if (countRow1 >= 5) return 1;
        }
        else if (newArr[y * n + i] == 2)
        {
            countRow2++;
            countRow1 = 0;
            if (countRow2 >= 5) return  2;
        }
        else
        {
           /* if (countRow1 == 4 && space == 1)
                return 1;
            if (countRow2 == 4 && space == 1)
                return 2;
            countRow1 = 0;
            countRow2 = 0;
            if (newArr[y * n + i + 1] != 0)
             if (space == 0) space++;
            else space = 0;*/
            if (countRow1 == 4) return 3;
            if (countRow2 == 4) return 4;
            countRow1 = 0;
            countRow2 = 0;
        }
        i++;
    }

    int countCol1 = 0;
    int countCol2 = 0;
    space = 0;
    i = 0;
    while (i < n) {
        if (newArr[i * n + x] == 1)
        {
            countCol1++;
            countCol2 = 0;
            if (countCol1 >= 5) return 1;
        }
        else if (newArr[i * n + x] == 2)
        {
            countCol1 = 0;
            countCol2++;
            if (countCol2 >= 5 ) return  2;
        }
        else
        {
          /*  if (countCol1 == 4 && space == 1)
                return 1;
            if (countCol2 == 4 && space == 1)
                return 2;
            countCol1 = 0;
            countCol2 = 0;
            if (newArr[(i + 1)* n + x] != 0)
            {
                if (space == 0) space++;
            }
            else space = 0;*/
            if (countCol1 == 4) return 3;
            if (countCol2 == 4) return 4;
            countCol1 = 0;
            countCol2 = 0;
        }
        i++;
    }

    int countDiagX1 = 0;
    int countDiagX2 = 0;
    space = 0;
    int temp;
    if (y + x >= n)
    {
        temp = x - (n - 1 - y);
        i = temp;
        while (i < n) {
            if (newArr[(n - (i - temp) - 1) * n + i] == 1) {
                countDiagX1++;
                countDiagX2 = 0;
                if (countDiagX1 >= 5) return 1;
            }
            else if (newArr[(n - (i - temp) - 1) * n + i] == 2) {
                countDiagX2++;
                countDiagX1 = 0;
                if (countDiagX2 >= 5) return 2;
            }
            else
            {
               /* if (countDiagX1 == 4 && space == 1)
                    return 1;
                if (countDiagX2 == 4 && space == 1)
                    return 2;
                countDiagX1 = 0;
                countDiagX2 = 0;

                    if (newArr[((n - ((i + 1) - temp) - 1) * n + (i + 1))] != 0) {
                        if (space == 0) space++;
                    }
                    else space = 0;*/
                if (countDiagX1 == 4) return 3;
                if (countDiagX2 == 4) return 4;
                countDiagX1 = 0;
                countDiagX2 = 0;
            }
            i++;
        }
    }
    else
    {
        space = 0;
        temp = x + y;
        i = 0;
        while (i <= temp) {
            if (newArr[(temp - i) * n + i] == 1) {
                countDiagX1++;
                countDiagX2 = 0;
                if (countDiagX1 >= 5) return 1;
            }
            else if (newArr[(temp - i) * n + i] == 2) {
                countDiagX2++;
                countDiagX1 = 0;
                if (countDiagX2 >= 5) return 2;
            }
            else
            {
              /*  if (countDiagX1 == 4 && space == 1)
                    return 1;
                if (countDiagX2 == 4 && space == 1)
                    return 2;
                countDiagX1 = 0;
                countDiagX2 = 0;
                    if (newArr[(temp - (i + 1)) * n + (i + 1)] != 0) {
                        if (space == 0) space++;
                    }
                    else space = 0;*/
                if (countDiagX1 == 4) return 3;
                if (countDiagX2 == 4) return 4;
                countDiagX1 = 0;
                countDiagX2 = 0;

            }
            i++;
        }
    }

    int countDiagY1 = 0;
    int countDiagY2 = 0;
    space = 0;
    if (x - y >= 0)
    {
        temp = x - y;
        i = temp;
        while (i < n) {
            if (newArr[(i - temp) * n + i] == 1) {
                countDiagY1++;
                countDiagY2 = 0;
                if (countDiagY1 >= 5) return 1;
            }
            else if (newArr[(i - temp) * n + i] == 2) {
                countDiagY2++;
                countDiagY1 = 0;
                if (countDiagY2 >= 5) return 2;
            }
            else
            {
                /*if (countDiagY1 == 4 && space == 1)
                    return 1;
                if (countDiagY2 == 4 && space == 1)
                    return 2;
                countDiagY1 = 0;
                countDiagY2 = 0;
                    if (newArr[((i + 1) - temp) * n + (i + 1)] != 0) {
                        space++;
                    }
                    else space = 0;*/
                if (countDiagY1 == 4) return 3;
                if (countDiagY2 == 4) return 4;
                countDiagY1 = 0;
                countDiagY2 = 0;
            }
            i++;
        }
    }
    else
    {
        temp = x + (n - 1 - y);
        i = 0;
        space = 0;
        while (i <= temp) {
            if (newArr[((n - temp) + i - 1) * n + i] == 1) {
                countDiagY1++;
                countDiagY2 = 0;
                if (countDiagY1 >= 5) return 1;
                if ((i == 0 || i == n - 1) && space == 0) space++;
            }
            else if (newArr[((n - temp) + i - 1) * n + i] == 2) {
                countDiagY2++;
                countDiagY1 = 0;
                if (countDiagY2 >= 5) return 2;
                if ((i == 0 || i == n - 1) && space == 0) space++;
            }
            else
            {
              /*if (countDiagY1 == 4 && space == 1)
                    return 1;
                if (countDiagY2 == 4 && space == 1)
                    return 2;
                countDiagY1 = 0;
                countDiagY2 = 0;
                    if (newArr[((n - temp) + (i + 1) - 1) * n + i + 1] != 0) {
                        if (space == 0) space++;
                    }
                    else space = 0;*/
                if (countDiagY1 == 4) return 3;
                if (countDiagY2 == 4) return 4;
                countDiagY1 = 0;
                countDiagY2 = 0;
            }
            i++;
        }
    }

    (*env)->ReleaseIntArrayElements(env, localArray, newArr, 0);
    (*env)->DeleteLocalRef(env, arr);
    //(*env)->Delete

    return 0;
}