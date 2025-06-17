package com.example.demo.service

import com.example.demo.model.User
import com.example.demo.repository.UserRepository
import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.Gauge
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicInteger
import jakarta.annotation.PostConstruct

@Service
class MetricsService(
    private val meterRegistry: MeterRegistry,
    private val userRepository: UserRepository
) {
    
    private lateinit var userCreatedCounter: Counter
    
    // Age range counters
    private val ageRange0to18 = AtomicInteger(0)
    private val ageRange19to35 = AtomicInteger(0)
    private val ageRange36to50 = AtomicInteger(0)
    private val ageRange51to65 = AtomicInteger(0)
    private val ageRangeOver65 = AtomicInteger(0)

    @PostConstruct
    fun initializeMetrics() {
        // Total users gauge
        Gauge.builder("users_total", userRepository) { repo ->
            repo.count().toDouble()
        }.description("Total number of users in the database")
         .register(meterRegistry)
        
        // User created counter
        userCreatedCounter = Counter.builder("users_created_total")
            .description("Total number of users created")
            .register(meterRegistry)
        
        // Age range gauges
        Gauge.builder("users_age_range_0_to_18", ageRange0to18) { counter ->
            counter.get().toDouble()
        }.description("Number of users aged 0-18")
         .register(meterRegistry)
            
        Gauge.builder("users_age_range_19_to_35", ageRange19to35) { counter ->
            counter.get().toDouble()
        }.description("Number of users aged 19-35")
         .register(meterRegistry)
            
        Gauge.builder("users_age_range_36_to_50", ageRange36to50) { counter ->
            counter.get().toDouble()
        }.description("Number of users aged 36-50")
         .register(meterRegistry)
            
        Gauge.builder("users_age_range_51_to_65", ageRange51to65) { counter ->
            counter.get().toDouble()
        }.description("Number of users aged 51-65")
         .register(meterRegistry)
            
        Gauge.builder("users_age_range_over_65", ageRangeOver65) { counter ->
            counter.get().toDouble()
        }.description("Number of users aged over 65")
         .register(meterRegistry)
        
        // Initialize age distribution on startup
        updateAgeDistribution()
    }
    
    fun recordUserCreated(user: User) {
        userCreatedCounter.increment()
        updateAgeDistribution()
    }
    
    private fun updateAgeDistribution() {
        val users = userRepository.findAll()
        
        // Reset counters
        ageRange0to18.set(0)
        ageRange19to35.set(0)
        ageRange36to50.set(0)
        ageRange51to65.set(0)
        ageRangeOver65.set(0)
        
        // Count users in each age range
        users.forEach { user ->
            when (user.age) {
                in 0..18 -> ageRange0to18.incrementAndGet()
                in 19..35 -> ageRange19to35.incrementAndGet()
                in 36..50 -> ageRange36to50.incrementAndGet()
                in 51..65 -> ageRange51to65.incrementAndGet()
                else -> ageRangeOver65.incrementAndGet()
            }
        }
    }
    
    fun getAgeDistribution(): Map<String, Int> {
        updateAgeDistribution()
        return mapOf(
            "0-18" to ageRange0to18.get(),
            "19-35" to ageRange19to35.get(),
            "36-50" to ageRange36to50.get(),
            "51-65" to ageRange51to65.get(),
            "65+" to ageRangeOver65.get()
        )
    }
} 