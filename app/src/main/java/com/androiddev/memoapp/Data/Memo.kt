package com.androiddev.memoapp.Data

import java.io.Serializable
import java.util.*

data class Memo(val memoTitle: String, val memoDesc: String, val date: Date) : Serializable


