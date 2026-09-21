
package com.gym.management.member.controller;

import com.gym.management.member.dto.GymMemberDtos.EmergencyContactDto;
import com.gym.management.member.dto.GymMemberDtos.MedicalInformationDto;
import com.gym.management.member.dto.GymMemberDtos.MemberDocumentDto;
import com.gym.management.member.dto.GymMemberDtos.MemberDto;
import com.gym.management.member.dto.GymMemberDtos.MemberNoteDto;
import com.gym.management.member.dto.GymMemberDtos.TrainerAssignmentDto;
import com.gym.management.member.service.GymMemberServices.EmergencyContactService;
import com.gym.management.member.service.GymMemberServices.MedicalInformationService;
import com.gym.management.member.service.GymMemberServices.MemberDocumentService;
import com.gym.management.member.service.GymMemberServices.MemberNoteService;
import com.gym.management.member.service.GymMemberServices.MemberService;
import com.gym.management.member.service.GymMemberServices.TrainerAssignmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * All REST controllers for the member module, grouped into one file as
 * nested static classes. Spring picks up @RestController-annotated nested
 * static classes the same way as top-level ones, so each still registers
 * its own @RequestMapping independently.
 */
public final class GymMemberControllers {

    private GymMemberControllers() {
    }

    // ------------------------------------------------------------------
    // MemberController
    // ------------------------------------------------------------------

    @RestController
    @RequestMapping("/api/v1/members")
    @RequiredArgsConstructor
    public static class MemberController {

        private final MemberService memberService;

        @PostMapping
        public ResponseEntity<MemberDto.Response> create(@Valid @RequestBody MemberDto.Request request) {
            return ResponseEntity.status(HttpStatus.CREATED).body(memberService.create(request));
        }

        @GetMapping("/{id}")
        public ResponseEntity<MemberDto.Response> getById(@PathVariable UUID id) {
            return ResponseEntity.ok(memberService.getById(id));
        }

        @GetMapping
        public ResponseEntity<List<MemberDto.Response>> getAll(
                @RequestParam(required = false) UUID branchId) {
            return ResponseEntity.ok(branchId != null
                    ? memberService.getByBranchId(branchId)
                    : memberService.getAll());
        }

        @PutMapping("/{id}")
        public ResponseEntity<MemberDto.Response> update(
                @PathVariable UUID id, @Valid @RequestBody MemberDto.Request request) {
            return ResponseEntity.ok(memberService.update(id, request));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable UUID id) {
            memberService.delete(id);
            return ResponseEntity.noContent().build();
        }
    }

    // ------------------------------------------------------------------
    // EmergencyContactController
    // ------------------------------------------------------------------

    @RestController
    @RequestMapping("/api/v1/emergency-contacts")
    @RequiredArgsConstructor
    public static class EmergencyContactController {

        private final EmergencyContactService emergencyContactService;

        @PostMapping
        public ResponseEntity<EmergencyContactDto.Response> create(
                @Valid @RequestBody EmergencyContactDto.Request request) {
            return ResponseEntity.status(HttpStatus.CREATED).body(emergencyContactService.create(request));
        }

        @GetMapping("/{id}")
        public ResponseEntity<EmergencyContactDto.Response> getById(@PathVariable UUID id) {
            return ResponseEntity.ok(emergencyContactService.getById(id));
        }

        @GetMapping("/member/{memberId}")
        public ResponseEntity<List<EmergencyContactDto.Response>> getByMemberId(@PathVariable UUID memberId) {
            return ResponseEntity.ok(emergencyContactService.getByMemberId(memberId));
        }

        @PutMapping("/{id}")
        public ResponseEntity<EmergencyContactDto.Response> update(
                @PathVariable UUID id, @Valid @RequestBody EmergencyContactDto.Request request) {
            return ResponseEntity.ok(emergencyContactService.update(id, request));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable UUID id) {
            emergencyContactService.delete(id);
            return ResponseEntity.noContent().build();
        }
    }

    // ------------------------------------------------------------------
    // MedicalInformationController
    // ------------------------------------------------------------------

    @RestController
    @RequestMapping("/api/v1/medical-information")
    @RequiredArgsConstructor
    public static class MedicalInformationController {

        private final MedicalInformationService medicalInformationService;

        @PostMapping
        public ResponseEntity<MedicalInformationDto.Response> create(
                @Valid @RequestBody MedicalInformationDto.Request request) {
            return ResponseEntity.status(HttpStatus.CREATED).body(medicalInformationService.create(request));
        }

        @GetMapping("/{id}")
        public ResponseEntity<MedicalInformationDto.Response> getById(@PathVariable UUID id) {
            return ResponseEntity.ok(medicalInformationService.getById(id));
        }

        @GetMapping("/member/{memberId}")
        public ResponseEntity<MedicalInformationDto.Response> getByMemberId(@PathVariable UUID memberId) {
            return ResponseEntity.ok(medicalInformationService.getByMemberId(memberId));
        }

        @PutMapping("/{id}")
        public ResponseEntity<MedicalInformationDto.Response> update(
                @PathVariable UUID id, @Valid @RequestBody MedicalInformationDto.Request request) {
            return ResponseEntity.ok(medicalInformationService.update(id, request));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable UUID id) {
            medicalInformationService.delete(id);
            return ResponseEntity.noContent().build();
        }
    }

    // ------------------------------------------------------------------
    // MemberDocumentController
    // ------------------------------------------------------------------

    @RestController
    @RequestMapping("/api/v1/member-documents")
    @RequiredArgsConstructor
    public static class MemberDocumentController {

        private final MemberDocumentService memberDocumentService;

        @PostMapping
        public ResponseEntity<MemberDocumentDto.Response> create(
                @Valid @RequestBody MemberDocumentDto.Request request) {
            return ResponseEntity.status(HttpStatus.CREATED).body(memberDocumentService.create(request));
        }

        @GetMapping("/{id}")
        public ResponseEntity<MemberDocumentDto.Response> getById(@PathVariable UUID id) {
            return ResponseEntity.ok(memberDocumentService.getById(id));
        }

        @GetMapping("/member/{memberId}")
        public ResponseEntity<List<MemberDocumentDto.Response>> getByMemberId(@PathVariable UUID memberId) {
            return ResponseEntity.ok(memberDocumentService.getByMemberId(memberId));
        }

        @PutMapping("/{id}")
        public ResponseEntity<MemberDocumentDto.Response> update(
                @PathVariable UUID id, @Valid @RequestBody MemberDocumentDto.Request request) {
            return ResponseEntity.ok(memberDocumentService.update(id, request));
        }

        @PatchMapping("/{id}/verify")
        public ResponseEntity<MemberDocumentDto.Response> verify(
                @PathVariable UUID id, @Valid @RequestBody MemberDocumentDto.VerifyRequest request) {
            return ResponseEntity.ok(memberDocumentService.verify(id, request));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable UUID id) {
            memberDocumentService.delete(id);
            return ResponseEntity.noContent().build();
        }
    }

    // ------------------------------------------------------------------
    // MemberNoteController
    // ------------------------------------------------------------------

    @RestController
    @RequestMapping("/api/v1/member-notes")
    @RequiredArgsConstructor
    public static class MemberNoteController {

        private final MemberNoteService memberNoteService;

        @PostMapping
        public ResponseEntity<MemberNoteDto.Response> create(@Valid @RequestBody MemberNoteDto.Request request) {
            return ResponseEntity.status(HttpStatus.CREATED).body(memberNoteService.create(request));
        }

        @GetMapping("/{id}")
        public ResponseEntity<MemberNoteDto.Response> getById(@PathVariable UUID id) {
            return ResponseEntity.ok(memberNoteService.getById(id));
        }

        @GetMapping("/member/{memberId}")
        public ResponseEntity<List<MemberNoteDto.Response>> getByMemberId(@PathVariable UUID memberId) {
            return ResponseEntity.ok(memberNoteService.getByMemberId(memberId));
        }

        @PutMapping("/{id}")
        public ResponseEntity<MemberNoteDto.Response> update(
                @PathVariable UUID id, @Valid @RequestBody MemberNoteDto.Request request) {
            return ResponseEntity.ok(memberNoteService.update(id, request));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable UUID id) {
            memberNoteService.delete(id);
            return ResponseEntity.noContent().build();
        }
    }

    // ------------------------------------------------------------------
    // TrainerAssignmentController
    // ------------------------------------------------------------------

    @RestController
    @RequestMapping("/api/v1/trainer-assignments")
    @RequiredArgsConstructor
    public static class TrainerAssignmentController {

        private final TrainerAssignmentService trainerAssignmentService;

        @PostMapping
        public ResponseEntity<TrainerAssignmentDto.Response> create(
                @Valid @RequestBody TrainerAssignmentDto.Request request) {
            return ResponseEntity.status(HttpStatus.CREATED).body(trainerAssignmentService.create(request));
        }

        @GetMapping("/{id}")
        public ResponseEntity<TrainerAssignmentDto.Response> getById(@PathVariable UUID id) {
            return ResponseEntity.ok(trainerAssignmentService.getById(id));
        }

        @GetMapping("/member/{memberId}")
        public ResponseEntity<List<TrainerAssignmentDto.Response>> getByMemberId(@PathVariable UUID memberId) {
            return ResponseEntity.ok(trainerAssignmentService.getByMemberId(memberId));
        }

        @GetMapping("/trainer/{trainerId}")
        public ResponseEntity<List<TrainerAssignmentDto.Response>> getByTrainerId(@PathVariable UUID trainerId) {
            return ResponseEntity.ok(trainerAssignmentService.getByTrainerId(trainerId));
        }

        @PutMapping("/{id}")
        public ResponseEntity<TrainerAssignmentDto.Response> update(
                @PathVariable UUID id, @Valid @RequestBody TrainerAssignmentDto.Request request) {
            return ResponseEntity.ok(trainerAssignmentService.update(id, request));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable UUID id) {
            trainerAssignmentService.delete(id);
            return ResponseEntity.noContent().build();
        }
    }
}
