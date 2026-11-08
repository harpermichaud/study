package io.github.harpertm.study.util

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

val backgroundScope = CoroutineScope(Dispatchers.Default + CoroutineName("Background"))

inline fun background(
	crossinline block: suspend () -> Unit
) { runBlocking { backgroundScope.launch { block() } } }