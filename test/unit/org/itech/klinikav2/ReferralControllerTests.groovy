package org.itech.klinikav2



import org.itech.klinikav2.domain.ReferralController;
import org.itech.klinikav2.domain.Referral;
import org.junit.*

import grails.test.mixin.*

/**
 * ReferralControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(ReferralController)
@Mock(Referral)
class ReferralControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/referral/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.referralInstanceList.size() == 0
        assert model.referralInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.referralInstance != null
    }

    void testSave() {
        controller.save()

        assert model.referralInstance != null
        assert view == '/referral/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/referral/show/1'
        assert controller.flash.message != null
        assert Referral.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/referral/list'


        populateValidParams(params)
        def referral = new Referral(params)

        assert referral.save() != null

        params.id = referral.id

        def model = controller.show()

        assert model.referralInstance == referral
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/referral/list'


        populateValidParams(params)
        def referral = new Referral(params)

        assert referral.save() != null

        params.id = referral.id

        def model = controller.edit()

        assert model.referralInstance == referral
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/referral/list'

        response.reset()


        populateValidParams(params)
        def referral = new Referral(params)

        assert referral.save() != null

        // test invalid parameters in update
        params.id = referral.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/referral/edit"
        assert model.referralInstance != null

        referral.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/referral/show/$referral.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        referral.clearErrors()

        populateValidParams(params)
        params.id = referral.id
        params.version = -1
        controller.update()

        assert view == "/referral/edit"
        assert model.referralInstance != null
        assert model.referralInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/referral/list'

        response.reset()

        populateValidParams(params)
        def referral = new Referral(params)

        assert referral.save() != null
        assert Referral.count() == 1

        params.id = referral.id

        controller.delete()

        assert Referral.count() == 0
        assert Referral.get(referral.id) == null
        assert response.redirectedUrl == '/referral/list'
    }
}
