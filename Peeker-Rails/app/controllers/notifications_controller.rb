# frozen_string_literal: true

class NotificationsController < ApplicationController
  before_action :set_notification, only: %i[show update destroy]

  # GET /notifications
  # GET /notifications.json
  def index
    @notifications = Current.user.notifications
  end

  # GET /notifications/1
  # GET /notifications/1.json
  def show; end

  # POST /notifications
  # POST /notifications.json
  def create
    @notification = Current.user.notifications.new(notification_params)

    if @notification.save
      render :show, status: :created, location: @notification
    else
      render json: @notification.errors, status: :unprocessable_entity
    end
  end

  # PATCH/PUT /notifications/1
  # PATCH/PUT /notifications/1.json
  def update
    if @notification.update(notification_params)
      render :show, status: :ok, location: @notification
    else
      render json: @notification.errors, status: :unprocessable_entity
    end
  end

  # DELETE /notifications/1
  # DELETE /notifications/1.json
  def destroy
    @notification.destroy
  end

  private

  # Use callbacks to share common setup or constraints between actions.
  def set_notification
    @notification = Current.user.notifications.find(params[:id])
  end

  # Only allow a list of trusted parameters through.
  def notification_params
    params.require(:notification).permit(:heading, :subtitle, :content, :data, :type, :user_id)
  end
end
