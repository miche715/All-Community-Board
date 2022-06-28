package com.example.server.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

@Entity
data class Good(@Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                @JsonProperty(value = "good_id")
                @Column(name = "good_id")
                var goodId: Long? = null,

                @ManyToOne
                @JsonIgnore
                @JoinColumn(name = "content_id")
                var content: Content? = null,

                @ManyToOne
                @JsonIgnore
                @JoinColumn(name = "user_id")
                var user: User? = null
)
