package com.ydh.budayabyl.utils

import com.ydh.budayabyl.BuildConfig


class Constant {
    companion object{
        const val PREF_NAME = "com.ydh.budayabyl"
        const val PREF_TOKEN = "PREF_TOKEN"

        const val PREF_IS_LOGIN = "PREF_IS_LOGIN"
        const val TYPE_A = "Seni"
        const val TYPE_B = "Tradisi"
        const val TYPE_C = "Tempat"

        //firebase
        const val NOTIFICATION_ID = 123
        const val NOTIFICATION_CHANNEL_ID = "${BuildConfig.APPLICATION_ID}.fcm"
        const val NOTIFICATION_CHANNEL_NAME = "Budaya Boyolali Push Notification"

        //assets
//        const val PLACEHOLDER_IMAGE_PRODUCT = "https://firebasestorage.googleapis.com/v0/b/lapak-kita-991bd.appspot.com/o/placeholder_image_product.jpeg?alt=media&token=2275e2be-12a3-487b-b179-19fcfc56cdcc"
    }
}