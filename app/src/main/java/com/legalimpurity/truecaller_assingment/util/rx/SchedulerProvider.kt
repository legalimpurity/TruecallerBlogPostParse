package com.legalimpurity.truecaller_assingment.util.rx

import io.reactivex.Scheduler

interface SchedulerProvider
{
    fun ui(): Scheduler
    fun computation(): Scheduler
    fun io(): Scheduler
}