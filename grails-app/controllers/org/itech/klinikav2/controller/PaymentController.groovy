package org.itech.klinikav2.controller

import org.itech.klinikav2.domain.Payment;
import org.springframework.dao.DataIntegrityViolationException

/**
 * PaymentController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class PaymentController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [paymentInstanceList: Payment.list(params), paymentInstanceTotal: Payment.count()]
    }

    def create() {
        [paymentInstance: new Payment(params)]
    }

    def save() {
        def paymentInstance = new Payment(params)
        if (!paymentInstance.save(flush: true)) {
            render(view: "create", model: [paymentInstance: paymentInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'payment.label', default: 'Payment'), paymentInstance.id])
        redirect(action: "show", id: paymentInstance.id)
    }

    def show() {
        def paymentInstance = Payment.get(params.id)
        if (!paymentInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'payment.label', default: 'Payment'), params.id])
            redirect(action: "list")
            return
        }

        [paymentInstance: paymentInstance]
    }

    def edit() {
        def paymentInstance = Payment.get(params.id)
        if (!paymentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'payment.label', default: 'Payment'), params.id])
            redirect(action: "list")
            return
        }

        [paymentInstance: paymentInstance]
    }

    def update() {
        def paymentInstance = Payment.get(params.id)
        if (!paymentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'payment.label', default: 'Payment'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (paymentInstance.version > version) {
                paymentInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'payment.label', default: 'Payment')] as Object[],
                          "Another user has updated this Payment while you were editing")
                render(view: "edit", model: [paymentInstance: paymentInstance])
                return
            }
        }

        paymentInstance.properties = params

        if (!paymentInstance.save(flush: true)) {
            render(view: "edit", model: [paymentInstance: paymentInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'payment.label', default: 'Payment'), paymentInstance.id])
        redirect(action: "show", id: paymentInstance.id)
    }

    def delete() {
        def paymentInstance = Payment.get(params.id)
        if (!paymentInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'payment.label', default: 'Payment'), params.id])
            redirect(action: "list")
            return
        }

        try {
            paymentInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'payment.label', default: 'Payment'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'payment.label', default: 'Payment'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
